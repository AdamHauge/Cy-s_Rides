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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;

import domain.Offer;
import volley.OfferVolleyImpl;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private Intent i;
    private boolean backPressed = false;
    private GoogleMap googleMap;
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;
    private LatLng iowaState = new LatLng(42.0266187, -93.64646540000001);
    private float defaultZoom = 16.0f; //TODO determine a good zoom value

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
                populateMap();
            }
        });

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("Where would you like to go?");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), defaultZoom));
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        populateMap();

        connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        if(null == networkInfo) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout),
                    "Cy's Rides Requires\nInternet Connection", Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("Connect WIFI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iowaState, defaultZoom));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //TODO go to specific offer page
                return false;
            }
        });
        this.googleMap = googleMap;
    }

    public void populateMap() {
        OfferVolleyImpl volley = new OfferVolleyImpl(new Callback() {
            public void call(ArrayList<Offer> result) {
                Log.d("Array", result.toString());
                googleMap.clear();
                for(int i = 0; i < result.size(); i++) {
                    LatLng coordinates = result.get(i).getCoordinates();
                    String name = result.get(i).getDestination();
                    String description = result.get(i).getDescription();
                    googleMap.addMarker(new MarkerOptions()
                            .position(coordinates)
                            .title(name)
                            .snippet(description)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                onMapReady(googleMap);
            }
        });
        volley.execute();
        //TODO get pins for all ride requests. Add them to map if only user has car.
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
                break;
            case R.id.requests:
                i = new Intent(MainActivity.this, RideRequests.class);
                break;
            case R.id.offers:
                i = new Intent(MainActivity.this, RideOffers.class);
                break;
            case R.id.contacts:
                i = new Intent(MainActivity.this, Contacts.class);
                break;
            case R.id.createOffer:
                i = new Intent(MainActivity.this, CreateOffer.class);
                break;
            case R.id.createRequest:
                i = new Intent(MainActivity.this, CreateRequest.class);
                break;
            case R.id.createProfile:
                i = new Intent(MainActivity.this, CreateProfile.class);
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

        connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        if(null == networkInfo && R.id.logout != id) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout),
                    "Cy's Rides Requires\nInternet Connection", Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("Connect WIFI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
            });
            snackbar.show();
            return false;
        }
        else if(R.id.logout == id) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else {
            startActivity(i);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}