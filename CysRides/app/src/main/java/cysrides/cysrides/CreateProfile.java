package cysrides.cysrides;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.EmailVolley;
import volley.EmailVolleyImpl;
import volley.UserVolley;
import volley.UserVolleyImpl;

public class CreateProfile extends AppCompatActivity {

    private EditText netIDView;
    private EditText passwordView;
    private EditText fNameView;
    private EditText lNameView;
    private EditText venmoView;
    private EditText profileDescriptionView;
    private RadioButton driverRadioButton;
    private RadioButton passengerRadioButton;
    private RadioGroup radioGroup;

    private static final int RB1_ID = 1000; //first radio button id
    private static final int RB2_ID = 1001; //second radio button id

    private String netID;
    private String password;
    private String firstName;
    private String lastName;
    private String venmo;
    private String profileDescription;
    private String confirmationCode;
    private UserType userType;
    private Callback call;

    private UserIntentService userIntentService = new UserIntentServiceImpl();

    private UserVolley userVolley = new UserVolleyImpl(call);

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        passwordView = (EditText) findViewById(R.id.Password);
        fNameView = (EditText) findViewById(R.id.First_Name);
        lNameView = (EditText) findViewById(R.id.Last_Name);
        venmoView = (EditText) findViewById(R.id.Venmo);
        profileDescriptionView = (EditText) findViewById(R.id.Description);
        driverRadioButton = (RadioButton) findViewById(R.id.driverRadioButton);
        passengerRadioButton = (RadioButton) findViewById(R.id.passengerRadioButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        driverRadioButton.setId(RB1_ID);
        passengerRadioButton.setId(RB2_ID);
        radioGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == RB1_ID) {
                            userType = UserType.DRIVER;
                        } else if (checkedId == RB2_ID) {
                            userType = UserType.PASSENGER;
                        }
                    }
                });

    }

    public void onCreateProfileButtonClicked(View view) {
        netID = netIDView.getText().toString();
        if (!isEmailValid(netID)) {
            Toast.makeText(CreateProfile.this, "You did not enter an iastate.edu email", Toast.LENGTH_LONG).show();
        }

        password = passwordView.getText().toString();
        if (!isPasswordValid(password)) {
            Toast.makeText(CreateProfile.this, "You did not enter a valid password", Toast.LENGTH_LONG).show();
        }

        firstName = fNameView.getText().toString();
        lastName = lNameView.getText().toString();
        if (!isNameValid(firstName, lastName)) {
            Toast.makeText(CreateProfile.this, "You did not enter a valid name", Toast.LENGTH_LONG).show();
        }

        venmo = venmoView.getText().toString();
        if(!isVenmoValid(venmo)){
            Toast.makeText(CreateProfile.this, "You did not enter a valid venmo", Toast.LENGTH_LONG).show();
        }

        profileDescription = profileDescriptionView.getText().toString();
        if(!isDescriptionValid(profileDescription)){
            Toast.makeText(CreateProfile.this, "You did not enter a long enough profile description", Toast.LENGTH_LONG).show();
        }

        Random rand = new Random();
        confirmationCode = String.format("%04d", rand.nextInt(10000));

//        if (driverRadioButton.isChecked()) {
//            userType = UserType.DRIVER;
//        }
//        if(passengerRadioButton.isChecked()){
//            userType = UserType.PASSENGER;
//        }
//        int type = radioGroup.getCheckedRadioButtonId();
//        if(type == RB1_ID){
//            userType = UserType.DRIVER;
//        }
//        else if(type == RB2_ID){
//            userType = UserType.PASSENGER;
//        }

        if (!isTypeSelected()) {
            Toast.makeText(CreateProfile.this, "You did not select a user type", Toast.LENGTH_LONG).show();
        }

        List<Offer> offers = new ArrayList<Offer>();
        List<Request> requests = new ArrayList<Request>();

        if (inputsValid()) {
            UserInfo user = new UserInfo(netID, password, confirmationCode, firstName, lastName, venmo, profileDescription,
                    userType, 0, offers, requests);
            userVolley.createUser(CreateProfile.this, user);
            finish();

            i = userIntentService.createIntent(this, DialogConfirmationCode.class, user);

            EmailVolley emailVolley = new EmailVolleyImpl();
            emailVolley.sendEmail(user.getNetID(), "cysrides@gmail.com", "Welcome to Cy's Rides!",
                    ("Here's your confimation code: " + user.getConfirmationCode()), this.getApplicationContext());

            startActivity(i);
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
        if (password.length() > 8) {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDescriptionValid(String profileDescription) {
        if (profileDescription.length() > 10) {
            return true;
        }
        return false;
    }

    private boolean isNameValid(String firstName, String lastName) {
        if (firstName.length() > 1 && lastName.length() > 1) {
            for (int i = 0; i < firstName.length(); i++) {
                if (Character.isDigit(firstName.charAt(i))) {
                    return false;
                }
            }
            for (int i = 0; i < lastName.length(); i++) {
                if (Character.isDigit(lastName.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isVenmoValid(String venmo){
        if(venmo.length() > 2){
            return true;
        }
        return false;
    }

    private boolean isTypeSelected(){
        return (driverRadioButton.isSelected() || passengerRadioButton.isChecked());
    }

    private boolean inputsValid(){
        return (isEmailValid(netID) && isPasswordValid(password) && isDescriptionValid(profileDescription) &&
                isNameValid(firstName, lastName) && isVenmoValid(venmo) && isTypeSelected());
    }
}