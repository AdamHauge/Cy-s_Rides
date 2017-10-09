package cysrides.cysrides;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;
import volley.UserVolley;
import volley.UserVolleyImpl;

public class CreateProfile extends AppCompatActivity {

    private EditText netIDView;
    private EditText passwordView;
    private EditText fNameView;
    private EditText lNameView;
    private EditText venmoView;
    private EditText profileDescriptionView;

    private String netID;
    private String password;
    private String firstName;
    private String lastName;
    private String venmo;
    private String profileDescription;
    private String confirmationCode;

    private UserVolley userVolley = new UserVolleyImpl();

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netIDView = (EditText) findViewById(R.id.NetID);
        passwordView = (EditText) findViewById(R.id.Password);
        fNameView = (EditText) findViewById(R.id.First_Name);
        lNameView = (EditText) findViewById(R.id.Last_Name);
        venmoView = (EditText) findViewById(R.id.Venmo);
        profileDescriptionView = (EditText) findViewById(R.id.Description);
    }

    public void onCreateProfileButtonClicked(View view) {
        netID = netIDView.getText().toString();
        if(!isEmailValid(netID)){
            Toast.makeText(CreateProfile.this, "You did not enter an iastate.edu email", Toast.LENGTH_LONG).show();
        }
        password = passwordView.getText().toString();
        if(!isPasswordValid(password)){
            Toast.makeText(CreateProfile.this, "You did not enter a valid password", Toast.LENGTH_LONG).show();
        }
        firstName = fNameView.getText().toString();
        lastName = lNameView.getText().toString();
        venmo = venmoView.getText().toString();
        profileDescription = profileDescriptionView.getText().toString();
        Random rand = new Random();
        confirmationCode = String.format("%04d", rand.nextInt(10000));

        List<Offer> offers = new ArrayList<Offer>();
        List<Request> requests = new ArrayList<Request>();

        if(isEmailValid(netID) && isPasswordValid(password)){
            UserInfo user = new UserInfo(netID, password, confirmationCode, firstName, lastName, venmo, profileDescription,
                    UserType.DRIVER, 5, offers, requests);
            userVolley.createUser(CreateProfile.this, findViewById(R.id.drawer_layout), user);
            finish();
            //i = new Intent(this, ConfirmationCodeDialog.class);
            //startActivity(i);
            displayConfirmationInput();
        }
    }

    public void displayConfirmationInput() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setTitle("Confirmation Code")
                .setMessage("Enter your four-digit confirmation code below: ")
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String inputCode = input.toString();
                        if (inputCode.length() != 3) {
                            Toast.makeText(CreateProfile.this, "You did not enter a four-digit number", Toast.LENGTH_LONG).show();
                            //CROSS-REFERENCE CODE WITH USER CONFIRMATION CODE IN DATABASE
                        }
                    }
                })
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
}