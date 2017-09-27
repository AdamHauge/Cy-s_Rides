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

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;

public class CreateOffer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                EditText temp = (EditText) findViewById(R.id.Cost);
                double cost = 0.00;
                if (!temp.getText().toString().equals("")){
                    cost = Double.parseDouble(temp.getText().toString());
                    long factor = (long) Math.pow(10, 2);
                    cost = cost * factor;
                    long tmp = Math.round(cost);
                    cost = (double) tmp / factor;
                }

                temp = (EditText)findViewById(R.id.Dest);
                String dest = temp.getText().toString();
                boolean validDest = true;
                String splitDest[] = dest.split(",");
                if(dest.equals("")){
                    Toast.makeText(getApplicationContext(), "Destination is required", Toast.LENGTH_SHORT).show();
                    validDest = false;
                }else {
                    if(splitDest.length == 2) {
                        if (splitDest[1].charAt(0) != ' ') {
                            validDest = false;
                           }
                        if (!splitDest[0].matches("^[a-zA-Z ]+")) {
                            validDest = false;
                           }
                        if (!splitDest[1].matches("^[a-zA-Z ]+")) {
                            validDest = false;
                        }
                    } else{
                            validDest = false;
                        }
                    if(!validDest){
                        Toast.makeText(getApplicationContext(), "Make sure destination is correctly formated", Toast.LENGTH_SHORT).show();
                    }
                }

                temp = (EditText)findViewById(R.id.Description);
                String description = temp.getText().toString();

                temp = (EditText)findViewById(R.id.LeaveDate);
                String date = temp.getText().toString();

                String[] splitDate = date.split("/");
                boolean validDate = true;
                if(date.equals("")){
                    Toast.makeText(getApplicationContext(), "Date is required", Toast.LENGTH_SHORT).show();
                    validDate = false;
                }
                if(splitDate.length == 3 ){
                    if(!((splitDate[0].length() < 3 && splitDate[0].length() > 0)) || !splitDate[0].matches("^[0-9]+")){
                        Toast.makeText(getApplicationContext(), "split 1", Toast.LENGTH_SHORT).show();
                        validDate = false;
                    }
                    if(!((splitDate[1].length() < 3 && splitDate[1].length() > 0)) || !splitDate[1].matches("^[0-9]+")){
                        Toast.makeText(getApplicationContext(), "split2", Toast.LENGTH_SHORT).show();
                        validDate = false;
                    }
                    if(!(splitDate[2].length() == 4  || splitDate[2].length() == 2) || !splitDate[2].matches("^[0-9]+")){
                        Toast.makeText(getApplicationContext(), "split 3", Toast.LENGTH_SHORT).show();
                        validDate = false;
                    }
                }
                else{
                    validDate = false;
                }
                if(!validDate){
                        Toast.makeText(getApplicationContext(), "Date format is incorrect", Toast.LENGTH_SHORT).show();
                }
                UserInfo ui = new UserInfo("rcerveny@iastate.edu", "password", 42, "Ryan", "Cerveny",
                                           "venmo","description", UserType.DRIVER, (float) 5.0,
                                            new ArrayList<Offer>(), new ArrayList<Request>());
                if(validDest && validDate) {
                    Offer o = new Offer(UserType.DRIVER, cost, ui.getNetID(), dest, description, date);
                    o.viewOffer(o, CreateOffer.this);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Discard Request");
            alert.setMessage("This will discard your current offer. Continue anyway?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                finish();
                i = new Intent(CreateOffer.this, MainActivity.class);
                startActivity(i);
            }});
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();
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
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Discard Request");
            alert.setMessage("This will discard your current offer. Continue anyway?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                    i = new Intent(CreateOffer.this, ViewProfile.class);
                    startActivity(i);
                }});
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();

            //TODO Adam: backing out of profile after create offer page should return user to main screen
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        final MenuItem item = menuItem;
        AlertDialog.Builder alert;

        switch(item.getItemId()) {
            case R.id.profile:
            case R.id.requests:
            case R.id.offers:
            case R.id.contacts:
            case R.id.createOffer:
            case R.id.createRequest:
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Discard Request");
                alert.setMessage("This will discard your current offer. Continue anyway?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.profile:
                                Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                                i = new Intent(CreateOffer.this, ViewProfile.class);
                                startActivity(i);
                                break;
                            case R.id.requests:
                                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                                i = new Intent(CreateOffer.this, RideRequests.class);
                                startActivity(i);
                                break;
                            case R.id.offers:
                                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                                i = new Intent(CreateOffer.this, RideOffers.class);
                                startActivity(i);
                                break;
                            case R.id.contacts:
                                Toast.makeText(getApplicationContext(), "Contacts", Toast.LENGTH_SHORT).show();
                                i = new Intent(CreateOffer.this, Contacts.class);
                                startActivity(i);
                                break;
                            case R.id.createOffer:
                                Toast.makeText(getApplicationContext(), "Create Offer", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.createRequest:
                                Toast.makeText(getApplicationContext(), "Create Request", Toast.LENGTH_SHORT).show();
                                i = new Intent(CreateOffer.this, CreateRequest.class);
                                startActivity(i);
                                break;
                            default:
                                break;
                        }
                    }
                });
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
                break;
            case R.id.logout:
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        i = new Intent(CreateOffer.this, LoginActivity.class);
                        startActivity(i);
                    }});
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
