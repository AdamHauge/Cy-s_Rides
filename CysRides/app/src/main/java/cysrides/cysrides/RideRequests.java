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

import java.util.ArrayList;
import java.util.List;

import domain.Request;
import service.ActivityService;
import service.ActivityServiceImpl;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.RefreshService;
import service.RefreshServiceImpl;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import volley.RequestVolleyImpl;

public class RideRequests extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private NavigationService navigationService = new NavigationServiceImpl();
    private RefreshService refreshService = new RefreshServiceImpl();
    private ActivityService activityService = new ActivityServiceImpl();

    private Intent i;
    private SwipeRefreshLayout refresh;
    private ArrayAdapter<String> adapter;
    private List<Request> requests = new ArrayList<>();
    private List<String> destinations = new ArrayList<>();
    private FragmentManager fragmentManager = this.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* initialize page input/output items */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_requests_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        navigationService.hideMenuItems(menu, userIntentService.getUserFromIntent(this.getIntent()));

        /* initialize page refreshing to take input from user */
        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refresh.setColorSchemeColors(ContextCompat.getColor(this.getApplicationContext(), R.color.colorGold),
                ContextCompat.getColor(this.getApplicationContext(), R.color.colorCardinal));
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRequestsList();
            }
        });

        /* notify requests volley to pull from database */
        getRequestsList();

        /* display list of ride requests on screen */
        ListView listView = (ListView)findViewById(R.id.ride_requests_list);
        adapter = new ArrayAdapter<>(RideRequests.this, android.R.layout.simple_list_item_1, destinations);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                /* notify fragment handler to display fragment to user */
                ViewRequest viewRequest = new ViewRequest();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                viewRequest.setData(requests.get(position));
                viewRequest.setContext(RideRequests.this);

                fragmentTransaction.replace(R.id.ride_requests_activity, viewRequest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        /* check for internet connection */
        if(navigationService.checkInternetConnection(getApplicationContext())) {
            connectionPopUp();
        }
    }

    /*
     * Method that notifies request volley to pull all ride request data from database
     */
    @SuppressWarnings("unchecked")
    public void getRequestsList() {
        RequestVolleyImpl volley = new RequestVolleyImpl(this, new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if (result.get(0) instanceof Request) {
                        requests = (ArrayList<Request>) result;
                    }
                } catch(Exception e) {
                    requests = new ArrayList<>();
                }

                /* display all data to user */
                adapter.clear();
                destinations.clear();
                for(int i = 0; i < requests.size(); i++) {
                    destinations.add(requests.get(i).getDestination());
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
     * Method that handles back press
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_requests_activity);

        /* close drawer */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        /* close all fragments */
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else {
            /* return to main activity */
            finish();
            i = userIntentService.createIntent(RideRequests.this, MainActivity.class, userIntentService.getUserFromIntent(this.getIntent()));
            startActivity(i);
        }
    }

    /*
     * Method to handle user's menu item selection
     *
     * Param: selected item
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
            i = userIntentService.createIntent(RideRequests.this, ViewProfile.class, userIntentService.getUserFromIntent(this.getIntent()));
            i.putExtra("caller", "Ride Requests");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Method to handle user's menu item selection
     *
     * Param: selected item
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        i = navigationService.getNavigationIntent(item, RideRequests.this, this.getIntent());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_requests_activity);
        drawer.closeDrawer(GravityCompat.START);
        if(R.id.logout == id) {
            AlertDialog.Builder alert = navigationService.logOutButton(this.getApplicationContext());
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SaveSharedPreference.clearUsernamePassword(RideRequests.this);
                    startActivity(i);
                }});
            alert.show();

            return true;
        }
        else if(navigationService.checkInternetConnection(getApplicationContext())) {
            /* check for wifi connection */
            connectionPopUp();
            /* close drawer */
            return false;
        }
        else {
            /* close drawer and move to next activity */
            startActivity(i);
            return true;
        }
    }

    /*
     * insert option to connect to wifi
     */
    public void connectionPopUp() {
        Snackbar snackbar = activityService.setupConnection(this.getApplicationContext(), findViewById(R.id.contacts_activity));
        snackbar.show();
    }
}