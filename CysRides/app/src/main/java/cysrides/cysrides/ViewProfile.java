package cysrides.cysrides;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;
import volley.UserVolleyImpl;

public class ViewProfile extends AppCompatActivity {

    private UserInfo user;
    private ArrayList<UserInfo> users;
    String username;
    private TextView netIDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netIDView = (TextView) findViewById(R.id.netIDView);
        netIDView.setText("...");
        //netIDView.append(username + user.getFirstName());

        username = getIntent().getStringExtra("netID");
        getUsers();
        //getUserInfo(username);
        //fillProfile();
    }

    public void getUsers() {
        UserVolleyImpl volley = new UserVolleyImpl(new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if(result.get(0) instanceof UserInfo) {
                        users = (ArrayList<UserInfo>) result;
                    }
                } catch(Exception e) {
                    users = new ArrayList<>();
                }

                getUserInfo(username);
            }
        });
        volley.execute();
    }

    public void fillProfile(){
        netIDView.setText(user.getNetID());

    }

    private void getUserInfo(String netID) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getNetID().equals(netID)) {
                    user = users.get(i);
                }
            }
            //fillProfile();

            netIDView.setText(netIDView.getText(), TextView.BufferType.EDITABLE);
            netIDView.setText(username + user.getFirstName());

        }
        else {
            netIDView.setText("User is null");
        }
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
