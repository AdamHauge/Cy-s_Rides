package cysrides.cysrides;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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

    private UserVolley userVolley = new UserVolleyImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netIDView = (EditText) findViewById(R.id.NetID);
        passwordView = (EditText) findViewById(R.id.password);
        fNameView = (EditText) findViewById(R.id.First_Name);
        lNameView = (EditText) findViewById(R.id.Last_Name);
        venmoView = (EditText) findViewById(R.id.Venmo);
        profileDescriptionView = (EditText) findViewById(R.id.Description);
    }

    public void onCreateProfileButtonClicked(View view) {
        /*netID = netIDView.getText().toString();
        password = passwordView.getText().toString();
        firstName = fNameView.getText().toString();
        lastName = lNameView.getText().toString();
        venmo = venmoView.getText().toString();
        profileDescription = profileDescriptionView.getText().toString();

        List<Offer> offers = new ArrayList<Offer>();
        List<Request> requests = new ArrayList<Request>();

        UserInfo user = new UserInfo(netID, password, 1111, firstName, lastName, venmo, profileDescription,
                UserType.DRIVER, 5, offers, requests);

        userVolley.createUser(CreateProfile.this, findViewById(R.id.drawer_layout), user);
        finish();
        startActivity(getIntent());*/
    }
}
