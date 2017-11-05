package cysrides.cysrides;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.UserInfo;
import service.GmailSenderServiceImpl;
import service.LoginService;
import service.LoginServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.EmailVolley;
import volley.EmailVolleyImpl;
import volley.UserVolleyImpl;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private LoginService loginService = new LoginServiceImpl();
    private EmailVolley emailVolley = new EmailVolleyImpl();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ArrayList<UserInfo> users;
    private UserInfo user;


    void LoginActivity(){
        AutoCompleteTextView email = mEmailView;
        EditText password = mPasswordView;
    }

    public String getmEmailView() {
        return mEmailView.getText().toString();
    }

    public String getmPasswordView() {
        return mPasswordView.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        if(SaveSharedPreference.getUsernamePassword(LoginActivity.this).length() != 0) {
            String data[] = SaveSharedPreference.getUsernamePassword(LoginActivity.this).split(":");
            mEmailView.setText(data[0]);
            mPasswordView.setText(data[1]);
            login();
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendEmail();
                login();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Exit");
        alertbox.setMessage("Are you sure you want to exit?");
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                moveTaskToBack(true);
            }
        });
        alertbox.setNegativeButton("No", null);
        alertbox.show();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@iastate.edu");
    }


    private boolean isPasswordValid(String password) {
        if (password.length() > 8){
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String generateCode(){
        Random rand = new Random();

        return String.format("%04d", rand.nextInt(10000));
    }

    public void sendEmail(){
        String to = mEmailView.getText().toString();
        String from = "cysrides@iastate.edu";
        String subject = "Welcome to Cy's Rides!";
        String body = "Here's your confirmation code: " + generateCode();

        emailVolley.sendEmail(to, from, subject, body, this.getApplicationContext());
    }

    public void onRegisterClick(View view){
        //sendEmail(view);

        Intent i;
        i = new Intent(LoginActivity.this, CreateProfile.class);
        startActivity(i);
    }

    private void login() {
        UserVolleyImpl volley = new UserVolleyImpl(new Callback() {
            ArrayList<UserInfo> users;

            public void call(ArrayList<?> result) {
                try {
                    if(result.get(0) instanceof UserInfo) {
                        users = (ArrayList<UserInfo>) result;
                    }
                } catch(Exception e) {
                    users = new ArrayList<>();
                }

                UserInfo userInfo = loginService.getUserInfo(users, mEmailView.getText().toString(), mPasswordView.getText().toString());
                if(userInfo != null) {
                    SaveSharedPreference.setUsernamePassword(LoginActivity.this, userInfo.getNetID() + ":" + userInfo.getPassword());
                    Intent i = userIntentService.createIntent(LoginActivity.this, MainActivity.class, userInfo);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Not valid credentials", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        volley.execute();
    }
}

