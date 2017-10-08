package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private Intent i;
    private boolean backPressed = false;
    private GoogleMap googleMap;
    ArrayList<Place> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PlaceAutocompleteFragment placeAutoComplete;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                places = new ArrayList<>();
                googleMap.clear();
                onMapReady(googleMap);
            }
        });

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("Where would you like to go?");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("Maps", "Place selected: " + place.getName());
                places.add(place);
                onMapReady(googleMap);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(null == networkInfo) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer_layout),
                    "Cy's Rides Requires Internet Connection", Snackbar.LENGTH_INDEFINITE);
            
            mySnackbar.setAction("Connect WIFI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
            });
            mySnackbar.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO all code dealing with maps goes here
        if(places.size() > 0) {
            LatLng location = places.get(places.size() - 1).getLatLng();//new LatLng(42.0266187, -93.64646540000001);
            float zoomLevel = 16.0f; //zoom can go up to 21
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(location).title("My place"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
        }
        else {
            LatLng ames = new LatLng(42.0266187, -93.64646540000001);
            float zoomLevel = 16.0f;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ames, zoomLevel));
        }
        this.googleMap = googleMap;
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
            Snackbar.make(findViewById(R.id.drawer_layout), "Tap back again to exit", Snackbar.LENGTH_SHORT).show();
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
            i = new Intent(MainActivity.this, ViewProfile.class);
            i.putExtra("caller", "Main Activity");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.profile:
                i = new Intent(MainActivity.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.requests:
                i = new Intent(MainActivity.this, RideRequests.class);
                startActivity(i);
                break;
            case R.id.offers:
                i = new Intent(MainActivity.this, RideOffers.class);
                startActivity(i);
                break;
            case R.id.contacts:
                i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.createOffer:
                i = new Intent(MainActivity.this, CreateOffer.class);
                startActivity(i);
                break;
            case R.id.createRequest:
                i = new Intent(MainActivity.this, CreateRequest.class);
                startActivity(i);
                break;
            case R.id.createProfile:
                // FIXME Claudia, this is temporary right?
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
