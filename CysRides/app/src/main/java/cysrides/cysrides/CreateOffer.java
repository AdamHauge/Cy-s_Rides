package cysrides.cysrides;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
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

import java.util.Calendar;
import java.util.GregorianCalendar;

import domain.Group;
import domain.Offer;
import service.GroupService;
import service.GroupServiceImpl;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.OfferService;
import service.OfferServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;

public class CreateOffer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    UserIntentService userIntentService = new UserIntentServiceImpl();

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Place destination;
    private int year, month, day;
    private boolean dateChanged = false;
    private String description;
    private double cost;
    private Intent i;
    private OfferService offerService = new OfferServiceImpl();
    private GroupService groupService = new GroupServiceImpl();
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

        PlaceAutocompleteFragment placeAutoComplete;
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("Where are you going?");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
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
                boolean noCost = null == data.getText();
                boolean noDate = 0 == year;
                boolean allValid = false;

                if (noDestination || noCost || noDate) {
                    Snackbar.make(findViewById(R.id.submit), "All data fields required", Snackbar.LENGTH_LONG).show();
                }
                else {
                    allValid = true;
                    try {
                        cost = Double.parseDouble(data.getText().toString());
                    } catch (Exception e) {
                        cost = 0;
                        allValid = false;
                        Snackbar.make(findViewById(R.id.submit), "Cost must be a decimal number", Snackbar.LENGTH_LONG).show();
                    }

                    data = (EditText) findViewById(R.id.Description);
                    description = data.getText().toString();
                }

                if(allValid) {
                    Offer o = new Offer(cost, "email", (String) destination.getName(), destination.getLatLng(), description, new GregorianCalendar(year, month, day).getTime());
                    offerService.createOffer(CreateOffer.this, o);

                    /* Refresh the page */
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        if(navigationService.checkInternetConnection(getApplicationContext())) {
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
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Discard Request");
            alert.setMessage("This will discard your current offer. Continue anyway?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                finish();
//                i = new Intent(CreateOffer.this, MainActivity.class);
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
        i = this.getIntent();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Discard Offer");
        alert.setMessage("This will discard your current offer. Continue anyway?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                i = navigationService.getNavigationIntent(item, CreateOffer.this, i);

                if (R.id.logout == id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CreateOffer.this);
                    alert.setTitle("Logout");
                    alert.setMessage("Do you really want to logout?");
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SaveSharedPreference.clearUsernamePassword(CreateOffer.this);
                            startActivity(i);
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, null);
                    alert.show();

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.create_offer_activity);
                    drawer.closeDrawer(GravityCompat.START);
                    retValue = true;
                } else if (navigationService.checkInternetConnection(getApplicationContext())) {
                    connectionPopUp();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.create_offer_activity);
                    drawer.closeDrawer(GravityCompat.START);
                    retValue = false;
                } else {
                    startActivity(i);
                    retValue = true;
                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();

        return retValue;
    }

    public void connectionPopUp() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.create_offer_activity),
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