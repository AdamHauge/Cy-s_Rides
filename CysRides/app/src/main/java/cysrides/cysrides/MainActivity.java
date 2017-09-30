package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private Intent i;
    private boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO all code dealing with maps goes here
        LatLng ames = new LatLng(42.0266187, -93.64646540000001);
        float zoomLevel = 16.0f; //zoom can go up to 21
        googleMap.addMarker(new MarkerOptions().position(ames).title("Iowa State University"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ames, zoomLevel));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(backPressed) {
                moveTaskToBack(true);
                return;
            }
            backPressed = true;
            Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressed = false;
                }
            }, 2000);
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
            i = new Intent(MainActivity.this, ViewProfile.class);
            i.putExtra("caller", "Main Activity");
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
                i = new Intent(MainActivity.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.requests:
                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, RideRequests.class);
                startActivity(i);
                break;
            case R.id.offers:
                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, RideOffers.class);
                startActivity(i);
                break;
            case R.id.contacts:
                Toast.makeText(getApplicationContext(), "Contacts", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.createOffer:Toast.makeText(getApplicationContext(), "Create Offer", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, CreateOffer.class);
                startActivity(i);
                break;
            case R.id.createRequest:
                Toast.makeText(getApplicationContext(), "Create Request", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, CreateRequest.class);
                startActivity(i);
                break;
            case R.id.createProfile:
                // FIXME This is temporary right?
                i = new Intent(MainActivity.this, CreateProfile.class);
                startActivity(i);
                break;
            case R.id.logout:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        i = new Intent(MainActivity.this, LoginActivity.class);
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
