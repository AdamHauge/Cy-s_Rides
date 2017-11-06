package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import java.util.HashMap;
import java.util.List;

import domain.Offer;
import domain.Request;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.OfferVolleyImpl;
import volley.RequestVolleyImpl;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private NavigationService navigationService = new NavigationServiceImpl();

    private Intent i;
    private boolean backPressed = false;
    private GoogleMap googleMap;
    private List<Offer> offers = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private FragmentManager fragmentManager = this.getSupportFragmentManager();
    private LatLng iowaState = new LatLng(42.0266187, -93.64646540000001);
    private float defaultZoom = 15.0f;
    private HashMap<Marker, Offer> offerMarkers;
    private HashMap<Marker, Request> requestMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Initialize side drawer */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* Initialize page navigation */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Initialize menu */
        Menu menu = navigationView.getMenu();
        navigationService.hideMenuItems(menu, userIntentService.getUserFromIntent(this.getIntent()));

        /* Initialize Floating Action Button */
        findViewById(R.id.fab_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* when clicked, reinitialize map */
                populateMap();
            }
        });

        /* Initialize PlaceAutoComplete Fragment */
        PlaceAutocompleteFragment placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("Where would you like to go?");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                /* Move camera to display chosen location */
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), defaultZoom));
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        /* Initialize Google Map */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Notify ride offer and ride request volley to get data for map */
        populateMap();

        /* check for internet connection */
        if(navigationService.checkInternetConnection(getApplicationContext())) {
            connectionPopUp();
        }
    }

    /*
     * Method to handle Google Map display
     *
     * Param: GoogleMap to be displayed on screen
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /* initialize map to display Iowa State */
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iowaState, defaultZoom));

        /* handle marker click events */
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Offer offerData;
                Request requestData;

                /* Check if marker is for an offer and open fragment */
                if(null != (offerData = offerMarkers.get(marker))) {
                    ViewOffer viewOffer = new ViewOffer();
                    viewOffer.setData(offerData);
                    fragmentTransaction.replace(R.id.activity_main, viewOffer);
                }

                /* Check if marker is for a request and open fragment */
                else if(null != (requestData = requestMarkers.get(marker))) {
                    ViewRequest viewRequest = new ViewRequest();
                    viewRequest.setData(requestData);
                    fragmentTransaction.replace(R.id.activity_main, viewRequest);
                }

                /* error */
                else {
                    return false;
                }

                /* display fragment */
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;
            }
        });

        this.googleMap = googleMap;
    }

    /*
     * Method used to notify offer volley and requests volley to pull data from database
     */
    @SuppressWarnings("unchecked")
    public void populateMap() {
        switch(userIntentService.getUserFromIntent(getIntent()).getUserType()) {
            case ADMIN:
            case DRIVER:
                /* Admins and Drivers can see ride request data  and ride offer data */
                /* Notify request volley to get ride request data */

                new RequestVolleyImpl(this, new Callback() {
                    @Override
                    public void call(ArrayList<?> result) {
                        try {
                            if (result.get(0) instanceof Request) {
                                requests = (ArrayList<Request>) result;
                            }
                        } catch (Exception e) {
                            offers = new ArrayList<>();
                            e.printStackTrace();
                        }
                        createMarkers();
                    }
                }).execute();
            case PASSENGER:
                /* Passengers can only see ride offer data */
                /* Notify offer volley to get ride offer data */
                new OfferVolleyImpl(this, new Callback() {
                    @Override
                    public void call(ArrayList<?> result) {
                        try {
                            if (result.get(0) instanceof Offer) {
                                offers = (ArrayList<Offer>) result;
                            }
                        } catch (Exception e) {
                            offers = new ArrayList<>();
                            e.printStackTrace();
                        }
                        createMarkers();
                    }
                }).execute();
        }
    }

    /*
     * Creates Markers that are displayed on Google Map
     */
    public void createMarkers() {
        offerMarkers = new HashMap<>();
        requestMarkers = new HashMap<>();

        /* reset the map */
        googleMap.clear();

        /* for all offers received from volley, display a marker on Google Map */
        for(int i = 0; i < offers.size(); i++) {
            LatLng coordinates = offers.get(i).getCoordinates();
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            /* place marker in hash table */
            offerMarkers.put(marker, offers.get(i));
        }

        /* for all requests received from volley, display a marker on Google Map */
        for(int i = 0; i < requests.size(); i++) {
            LatLng coordinates = requests.get(i).getCoordinates();
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            /* for all requests received from volley, display a marker on Google Map */
            requestMarkers.put(marker, requests.get(i));
        }

        /* display map */
        onMapReady(googleMap);
    }

    /*
     * Method that handles back button press
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);

        /* if the drawer is open, close it */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        /* if there is a fragment open, close it */
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        /* close the app */
        else {
            if(backPressed) {
                moveTaskToBack(true);
                return;
            }
            backPressed = true;
            Snackbar.make(findViewById(R.id.activity_main), "Tap back again to exit", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, 2000);
        }
    }

    /*
     * method that initializes menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile_button, menu);
        return true;
    }

    /*
     * Method to handle user's menu item selection
     *
     * Param: selected item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            i = userIntentService.createIntent(MainActivity.this, ViewProfile.class, userIntentService.getUserFromIntent(this.getIntent()));
            i.putExtra("caller", "Main Activity");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * method to handle user's menu selection
     *
     * Param: selected menu item
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        i = navigationService.getNavigationIntent(item, MainActivity.this, this.getIntent());

        /* close drawer */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);

        /* check if the user  wants to logout */
        if(R.id.logout == id) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Logout");
            alert.setMessage("Do you really want to logout?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SaveSharedPreference.clearUsernamePassword(MainActivity.this);
                    startActivity(i);
                }});
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();
            return true;
        }
        /* check for internet connection before doing anything */
        else if(navigationService.checkInternetConnection(getApplicationContext())) {
            connectionPopUp();
            return false;
        }
        /* go to requested page */
        else {
            startActivity(i);
            return true;
        }
    }

    /*
     * insert option to connect to wifi
     */
    public void connectionPopUp() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main),
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
