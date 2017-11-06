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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

    //UI references fields
    private TextView netIDView;
    private TextView passwordView;
    private EditText fNameView;
    private EditText lNameView;
    private EditText venmoView;
    private EditText profileDescriptionView;
    private RadioButton driverRadioButton;
    private RadioButton passengerRadioButton;
    private RadioGroup radioGroup;
    private Button createProfileButton;

    private static final int RB1_ID = 1000; //first radio button id
    private static final int RB2_ID = 1001; //second radio button id

    //User object fields
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

    /*
    Initializes all UI components in the class to the ones in the xml file.
    Grabs username and password from the previous intent and adds then to the text fields.
    Listeners for radio group and create profile button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        netIDView = (TextView) findViewById(R.id.NetID);
        passwordView = (TextView) findViewById(R.id.Password);
        fNameView = (EditText) findViewById(R.id.First_Name);
        lNameView = (EditText) findViewById(R.id.Last_Name);
        venmoView = (EditText) findViewById(R.id.Venmo);
        profileDescriptionView = (EditText) findViewById(R.id.Description);
        createProfileButton = (Button) findViewById(R.id.createProfileButton);
        driverRadioButton = (RadioButton) findViewById(R.id.driverRadioButton);
        passengerRadioButton = (RadioButton) findViewById(R.id.passengerRadioButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        driverRadioButton.setId(RB1_ID);
        passengerRadioButton.setId(RB2_ID);

        netID = this.getIntent().getExtras().getString("netID");
        password = this.getIntent().getExtras().getString("password");

        netIDView.append(netID);
        passwordView.append(password);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == RB1_ID) {
                            userType = UserType.DRIVER;
                        } else if (checkedId == RB2_ID) {
                            userType = UserType.PASSENGER;
                        }
                    }
                });

        final Context context = this.getApplicationContext();

        /*
        When the profile button is selected, all inputs are verified. If inputs are invalid, a toast appears saying so.
        If all user inputs are valid, the user is created. An email is sent to the net-id the user entered.
        User is taken to the confirmation code page.
         */
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context currentContext = context;

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

                    i = userIntentService.createIntent(currentContext, DialogConfirmationCode.class, user);

                    EmailVolley emailVolley = new EmailVolleyImpl();
                    emailVolley.sendEmail(user.getNetID(), "cysrides@iastate.edu", "Welcome to Cy's Rides!",
                            ("Here's your confimation code: " + user.getConfirmationCode()), currentContext);

                    startActivity(i);
                }
            }
        });

    }

    /* An email is valid if it contains "@iastate.edu"*/
    private boolean isEmailValid(String email) {
        return email.contains("@iastate.edu");
    }

    /* A password is valid if it contains a digit and is at least eight characters long*/
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

    /* A description is valid if it is longer than 10 characters*/
    private boolean isDescriptionValid(String profileDescription) {
        if (profileDescription.length() > 10) {
            return true;
        }
        return false;
    }

    /* A name is valid if the first and last name are greater than one character and do not contain a digit*/
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

    /* A venmo is valid if it is longer than 2 characters*/
    private boolean isVenmoValid(String venmo){
        if(venmo.length() > 2){
            return true;
        }
        return false;
    }

    /* A user type is valid if the driver or the passenger button is selected*/
    private boolean isTypeSelected(){
        return (driverRadioButton.isSelected() || passengerRadioButton.isChecked());
    }

    /* All inputs are valid if the aforementioned methods are true*/
    private boolean inputsValid(){
        return (isEmailValid(netID) && isPasswordValid(password) && isDescriptionValid(profileDescription) &&
                isNameValid(firstName, lastName) && isVenmoValid(venmo) && isTypeSelected());
    }
}