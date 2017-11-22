package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

import domain.Offer;
import service.ActivityService;
import service.ActivityServiceImpl;
import service.Callback;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.RefreshService;
import service.RefreshServiceImpl;
import service.SearchCallback;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.OfferVolleyImpl;

public class RideOffers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private NavigationService navigationService = new NavigationServiceImpl();
    private RefreshService refreshService = new RefreshServiceImpl();
    private ActivityService activityService = new ActivityServiceImpl();

    private Intent i;
    private SwipeRefreshLayout refresh;
    private ArrayAdapter<String> adapter;
    private List<Offer> offers = new ArrayList<>();
    private List<String> destinations = new ArrayList<>();
    private FragmentManager fragmentManager = this.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_offers_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        navigationService.hideMenuItems(menu, userIntentService.getUserFromIntent(this.getIntent()));

        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refresh.setColorSchemeColors(ContextCompat.getColor(RideOffers.this,
                R.color.colorGold), ContextCompat.getColor(RideOffers.this, R.color.colorCardinal));
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOffersList();
            }
        });
        getOffersList();

        /* display list of ride offers on screen */
        i = this.getIntent();
        ListView listView = (ListView)findViewById(R.id.ride_offers_list);
        adapter = new ArrayAdapter<>(RideOffers.this, android.R.layout.simple_list_item_1, destinations);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                /* notify fragment manager to display ride offer information */
                RideFragment viewOffer = new ViewOffer();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                viewOffer.setData(offers.get(position));
                viewOffer.setContext(RideOffers.this);
                viewOffer.setUserInfo(userIntentService.getUserFromIntent(i));
                fragmentTransaction.replace(R.id.ride_offers_activity, viewOffer);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if(navigationService.checkInternetConnection(RideOffers.this)) {
            connectionPopUp();
        }
    }

    /*
     * Method which notifies ride offer volley to pull ride offer data from database
     */
    @SuppressWarnings("unchecked")
    public void getOffersList() {
        /* notify offer volley to pull data */
        OfferVolleyImpl volley = new OfferVolleyImpl(this, new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if (result.get(0) instanceof Offer) {
                        offers = (ArrayList<Offer>) result;
                    }
                } catch(Exception e) {
                    offers = new ArrayList<>();
                    e.printStackTrace();
                }

                /* display data to user */
                adapter.clear();
                destinations.clear();
                for(int i = 0; i < offers.size(); i++) {
                    destinations.add(offers.get(i).getDestination());
                }

                /* stop refreshing page */
                if(refresh.isRefreshing()) {
                    refreshService.stopRefreshing(refresh, adapter);
                }
                else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        volley.execute();
    }

    /*
    * Method that handles back button press
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_offers_activity);
        /* close drawer */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        /* close any open fragment */
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        /* return to main activity */
        else {
            finish();
            i = userIntentService.createIntent(RideOffers.this, MainActivity.class, userIntentService.getUserFromIntent(this.getIntent()));
            startActivity(i);
        }
    }

    /*
     * Initialize options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_and_search, menu);
        return true;
    }

    /*
     * Moves to new page based on user selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (R.id.my_profile == id) {
            i = userIntentService.createIntent(RideOffers.this, ViewProfile.class, userIntentService.getUserFromIntent(this.getIntent()));
            i.putExtra("caller", "Ride Offers");
            startActivity(i);
        }

        else if(R.id.search == id) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            RideSearch rideSearch = new RideSearch();
            rideSearch.setCallback(new SearchCallback() {
                @Override
                public void call(Place place) {
                    onBackPressed();

                    //TODO filter results
                }
            });

            fragmentTransaction.replace(R.id.ride_offers_activity, rideSearch);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Moves to new page based on user selection
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        i = navigationService.getNavigationIntent(item, RideOffers.this, this.getIntent());

        /* check if user wants to log out */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_offers_activity);
        drawer.closeDrawer(GravityCompat.START);
        if(R.id.logout == id) {
            AlertDialog.Builder alert = navigationService.logOutButton(RideOffers.this);
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SaveSharedPreference.clearUsernamePassword(RideOffers.this);
                    startActivity(i);
                }});
            alert.show();

            return true;
        }
        /* check if user needs to connect to wifi */
        else if(navigationService.checkInternetConnection(RideOffers.this)) {
            connectionPopUp();
            return false;
        }
        /* move to desired page */
        else {
            startActivity(i);
            return true;
        }
    }

    /*
     * insert option to connect to wifi
     */
    public void connectionPopUp() {
        Snackbar snackbar = activityService.setupConnection(RideOffers.this, findViewById(R.id.contacts_activity));
        snackbar.show();
    }
}