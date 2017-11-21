package cysrides.cysrides;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import domain.Offer;
import service.ActivityService;
import service.ActivityServiceImpl;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.OfferService;
import service.OfferServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;

public class CreateOffer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    UserIntentService userIntentService = new UserIntentServiceImpl();
    private ActivityService activityService = new ActivityServiceImpl();

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Place destination;
    private Place start;
    private int year, month, day;
    private boolean dateChanged = false;
    private String description;
    private double cost;
    private Intent i;
    private OfferService offerService = new OfferServiceImpl();
    private NavigationService navigationService = new NavigationServiceImpl();
    boolean retValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.create_offer_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        navigationService.hideMenuItems(menu, userIntentService.getUserFromIntent(this.getIntent()));

        /* initialize all data input points */

        PlaceAutocompleteFragment destinationAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.offer_destination_autocomplete);
        destinationAutoComplete.setHint("Where are you going?");
        destinationAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                destination = place;
                Log.d("Maps", "Place selected: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        PlaceAutocompleteFragment startAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.offer_start_autocomplete);
        startAutoComplete.setHint("Where are you leaving from?");
        startAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                start = place;
                Log.d("Maps", "Place selected: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        EditText displayDate = (EditText) findViewById(R.id.LeaveDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOffer.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                        dateSetListener, year, month, day);

                datePickerDialog.setCancelable(false);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            if(!dateChanged) {
                                year = 0;
                                month = 0;
                                day = 0;
                            }
                        }
                    }
                });
                if(null != datePickerDialog.getWindow()) {
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                month = m + 1;
                day = d;
                year = y;
                Log.d("CreateOffer", "onDateSet date: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                EditText editText = (EditText)findViewById(R.id.LeaveDate);
                editText.setText(date);
                dateChanged = true;
            }
        };

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.Cost);
                boolean noDestination = null == destination;
                boolean noStart = null == start;
                boolean noCost = null == data.getText();
                boolean noDate = 0 == year;
                boolean allValid = false;

                /* check that all input data is valid */
                if (noDestination || noStart || noCost || noDate) {
                    Snackbar.make(findViewById(R.id.submit), "All data fields required", Snackbar.LENGTH_LONG).show();
                }
                else {
                    allValid = true;
                    /* check that cost is valid */
                    try {
                        cost = Double.parseDouble(data.getText().toString());
                    } catch (Exception e) {
                        cost = 0;
                        allValid = false;
                        Snackbar.make(findViewById(R.id.submit), "Cost must be a decimal number", Snackbar.LENGTH_LONG).show();
                    }
                    /* check that start and end locations are valid */
                    try {
                        Geocoder gcd = new Geocoder(CreateOffer.this, Locale.getDefault());
                        List<Address> destAddress = gcd.getFromLocation(destination.getLatLng().latitude, destination.getLatLng().longitude, 1);
                        List<Address> startAddress = gcd.getFromLocation(start.getLatLng().latitude, start.getLatLng().longitude, 1);

                        if(!destAddress.get(0).getLocality().equals("Ames") && !startAddress.get(0).getLocality().equals("Ames") &&
                                !destAddress.get(0).getAdminArea().equals("Iowa") && !startAddress.get(0).getAdminArea().equals("United States")) {
                            allValid = false;
                            Snackbar.make(findViewById(R.id.submit), "Ride must start or end in Ames, IA", Snackbar.LENGTH_LONG).show();
                        }

                        if(!destAddress.get(0).getCountryName().equals("United States") || !startAddress.get(0).getCountryName().equals("United States")) {
                            allValid = false;
                            Snackbar.make(findViewById(R.id.submit), "Ride must start and end in United States", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    data = (EditText) findViewById(R.id.Description);
                    description = data.getText().toString();
                }

                if(allValid) {
                    Offer o = new Offer(cost, userIntentService.getUserFromIntent(
                            getIntent()).getNetID(), (String) destination.getName(),
                            destination.getLatLng(), (String) start.getName(), start.getLatLng(),
                            description, new GregorianCalendar(year, month, day).getTime());

                    offerService.createOffer(CreateOffer.this, o);

                    /* Refresh the page */
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        if(navigationService.checkInternetConnection(CreateOffer.this)) {
            connectionPopUp();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.create_offer_activity);
        i = userIntentService.createIntent(CreateOffer.this, MainActivity.class, userIntentService.getUserFromIntent(this.getIntent()));
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /* discard current request */
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Discard Request");
            alert.setMessage("This will discard your current offer. Continue anyway?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                finish();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        i = navigationService.getNavigationIntent(item, CreateOffer.this, this.getIntent());
        AlertDialog.Builder alert;

        /* check if user wants to log out */
        if (R.id.logout == item.getItemId()) {
            alert = navigationService.logOutButton(CreateOffer.this);
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SaveSharedPreference.clearUsernamePassword(CreateOffer.this);
                    startActivity(i);
                }
            });
            alert.show();
            retValue = true;
        }
        else { /* check if user wants to discard their request */
            alert = new AlertDialog.Builder(this);
            alert.setTitle("Discard Offer");
            alert.setMessage("This will discard your current offer. Continue anyway?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Handle navigation view item clicks here.
                    i = navigationService.getNavigationIntent(item, CreateOffer.this, i);

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.create_offer_activity);
                    drawer.closeDrawer(GravityCompat.START);

                    if (navigationService.checkInternetConnection(CreateOffer.this)) {
                        connectionPopUp();
                        retValue = false;
                    } else {
                        startActivity(i);
                        retValue = true;
                    }
                }
            });
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();
        }
        return retValue;
    }

    /*
     * insert option to connect to wifi
     */
    public void connectionPopUp() {
        Snackbar snackbar = activityService.setupConnection(CreateOffer.this, findViewById(R.id.contacts_activity));
        snackbar.show();
    }
}