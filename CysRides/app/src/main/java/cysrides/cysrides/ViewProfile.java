package cysrides.cysrides;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.UserVolleyImpl;

public class ViewProfile extends AppCompatActivity {

    private UserIntentService userIntentService = new UserIntentServiceImpl();

    private UserInfo user;
    private TextView netIDView;
    private TextView firstNameView;
    private TextView lastNameView;
    private TextView descriptionView;
    private TextView venmoView;
    private RatingBar userRatingBar;

    /*
    When this activity is created, it initializes all the UI components to the values of the UserInfo
    object.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netIDView = (TextView) findViewById(R.id.netIDView);
        firstNameView = (TextView) findViewById(R.id.firstNameView);
        lastNameView = (TextView) findViewById(R.id.lastNameView);
        descriptionView = (TextView) findViewById(R.id.descriptionView);
        venmoView = (TextView) findViewById(R.id.venmoView);
        userRatingBar = (RatingBar) findViewById((R.id.userRatingBar));

        user = userIntentService.getUserFromIntent(this.getIntent());

        netIDView.setText(user.getNetID().split("@iastate.edu")[0]);
        firstNameView.setText(user.getFirstName());
        lastNameView.setText(user.getLastName());
        descriptionView.setText(user.getProfileDescription());
        venmoView.append(user.getVenmoName());
        userRatingBar.setRating(user.getUserRating());
    }

    /*
    On back pressed, the app screen returns to the Main Activity.
     */
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = userIntentService.createIntent(ViewProfile.this, MainActivity.class, userIntentService.getUserFromIntent(this.getIntent()));
        startActivity(intent);
    }
}
