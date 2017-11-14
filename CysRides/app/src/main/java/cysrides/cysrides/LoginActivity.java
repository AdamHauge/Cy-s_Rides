package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import domain.UserInfo;
import service.Callback;
import service.LoginService;
import service.LoginServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.EmailVolley;
import volley.EmailVolleyImpl;
import volley.UserVolleyImpl;

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

        /* if a user is already logged in, use their profile */
        if(0 != SaveSharedPreference.getUsernamePassword(LoginActivity.this).length()) {
            String data[] = SaveSharedPreference.getUsernamePassword(LoginActivity.this).split(":");
            login(data[0], data[1]);
        }
        else {
            setContentView(R.layout.activity_login);

            // Set up the login form.
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login(mEmailView.getText().toString(), mPasswordView.getText().toString());
                }
            });


            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
        }
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

        return String.format(Locale.US, "%04d", rand.nextInt(10000));
    }

    public void sendEmail(){
        String to = mEmailView.getText().toString();
        String from = "cysrides@iastate.edu";
        String subject = "Welcome to Cy's Rides!";
        String body = "Here's your confirmation code: " + generateCode();

        emailVolley.sendEmail(to, from, subject, body, this.getApplicationContext());
    }

    public void onRegisterClick(View view){
        if((isEmailValid(mEmailView.getText().toString()) && isPasswordValid(mPasswordView.getText().toString()))) {
            Intent i;
            i = new Intent(LoginActivity.this, CreateProfile.class);
            i.putExtra("netID", mEmailView.getText().toString());
            i.putExtra("password", mPasswordView.getText().toString());
            startActivity(i);
        }
        else if(!isEmailValid(mEmailView.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(), "Enter an iastate.edu email", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(!isPasswordValid(mPasswordView.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(), "Password must be longer than 8 characters and contain a digit", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @SuppressWarnings("unchecked")
    private void login(final String username, final String password) {
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

                UserInfo userInfo = loginService.getUserInfo(users, username, password);
                if(userInfo != null) {
                    SaveSharedPreference.setUsernamePassword(LoginActivity.this, userInfo.getNetID() + ":" + userInfo.getPassword());
                    Intent i = userIntentService.createIntent(LoginActivity.this, MainActivity.class, userInfo);
                    startActivity(i);
                } else {
                    /* For testing purposes, in case a user gets deleted from database, clear saved data and restart log in */
                    if(0 != SaveSharedPreference.getUsernamePassword(LoginActivity.this).length()) {
                        SaveSharedPreference.clearUsernamePassword(LoginActivity.this);
                        finish();
                        startActivity(getIntent());
                    }
                    else { /* User entered invalid credentials, everything else is fine */
                        Toast.makeText(getApplicationContext(), "Not valid credentials", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        volley.execute();
    }
}

