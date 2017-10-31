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

import org.w3c.dom.Text;

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
    private TextView firstNameView;
    private TextView lastNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netIDView = (TextView) findViewById(R.id.netIDView);
        firstNameView = (TextView) findViewById(R.id.firstNameView);
        lastNameView = (TextView) findViewById(R.id.lastNameView);

        username = getIntent().getStringExtra("netID");
        getUsers();
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

    private void getUserInfo(String netID) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getNetID().equals(netID)) {
                    user = users.get(i);
                }
            }

            //netIDView.setText(netIDView.getText(), TextView.BufferType.EDITABLE);
            netIDView.setText(username.split("@iastate.edu")[0]);

            //firstNameView.setText(firstNameView.getText(), TextView.BufferType.EDITABLE);
            firstNameView.setText(user.getFirstName());

            //lastNameView.setText(lastNameView.getText(), TextView.BufferType.EDITABLE);
            lastNameView.setText(user.getLastName());

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
