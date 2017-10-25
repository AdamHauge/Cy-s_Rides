package cysrides.cysrides;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import domain.UserInfo;
import volley.UserVolleyImpl;

public class ViewProfile extends AppCompatActivity {

    private UserInfo user;
    String username;
    private TextView netIDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //netIDView.getText().append(netID);
//        username = getIntent().getStringExtra("netID");
//        getUser(username);
//        netIDView.append(username + user.getFirstName());
    }

    public void getUser(String netID) {
        UserVolleyImpl volley = new UserVolleyImpl();
        //user = volley.onPostExecute(volley.doInBackground(netID));
    }

    public void fillProfile(){
        //netIDView.append(user.getNetID());

    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        String previous = intent.getStringExtra("caller");
        finish();
        if(previous.equals("Create Offer") || previous.equals("Create Request")) {
            intent = new Intent(ViewProfile.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }
}
