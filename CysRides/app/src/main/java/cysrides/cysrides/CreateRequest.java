package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;

public class CreateRequest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button submit = (Button) findViewById(R.id.CreateRequestButton);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText temp = (EditText)findViewById(R.id.numBagsInput);
                int numBags = Integer.parseInt(temp.getText().toString());

                temp = (EditText)findViewById(R.id.destInput);
                String dest = temp.getText().toString();

                temp = (EditText)findViewById(R.id.discriptionInput);
                String description = temp.getText().toString();

                temp = (EditText)findViewById(R.id.dateInput);
                String date = temp.getText().toString();

                UserInfo ui = new UserInfo("username", "password", "rcerveny@iastate.edu", UserType.DRIVER, "Ryan",
                        "Cerveny", "Venmo", (float)5.0 , new ArrayList<Offer>());

                Request r = new Request(numBags, ui, dest, description, date);

                r.viewRequest(r, CreateRequest.this);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
            i = new Intent(CreateRequest.this, ViewProfile.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.profile:
                Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.requests:
                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, RideRequests.class);
                startActivity(i);
                break;
            case R.id.login:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.contacts:
                Toast.makeText(getApplicationContext(), "Contacts", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.createOffer:Toast.makeText(getApplicationContext(), "Create Offer", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, CreateOffer.class);
                startActivity(i);
                break;
            case R.id.createRequest:
                Toast.makeText(getApplicationContext(), "Create Request", Toast.LENGTH_SHORT).show();
                i = new Intent(CreateRequest.this, CreateRequest.class);
                startActivity(i);
                break;
            case R.id.logout:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        i = new Intent(CreateRequest.this, LoginActivity.class);
                        startActivity(i);
                    }});
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

