package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
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
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import domain.Group;
import domain.Offer;
import domain.UserInfo;
import service.ActivityService;
import service.ActivityServiceImpl;
import service.Callback;
import service.DrawerLock;
import service.NavigationService;
import service.NavigationServiceImpl;
import service.RefreshService;
import service.RefreshServiceImpl;
import service.SearchCallback;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import service.UserRatingService;
import service.UserRatingServiceImpl;
import volley.ExpiredGroupVolleyImpl;
import volley.GroupVolleyImpl;
import volley.OfferVolleyImpl;

public class GroupRating extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLock {

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private NavigationService navigationService = new NavigationServiceImpl();
    private RefreshService refreshService = new RefreshServiceImpl();
    private ActivityService activityService = new ActivityServiceImpl();
    private UserRatingService userRatingService = new UserRatingServiceImpl();

    private Intent i;
    private DrawerLayout drawer;
    private SwipeRefreshLayout refresh;
    private ArrayAdapter<String> adapter;
    private ArrayList<Group> groups = new ArrayList<>();
    private List<String> groupMembers = new ArrayList<>();
    private TextView searchResult;
    private FragmentManager fragmentManager = this.getSupportFragmentManager();

    /**
     * Initializes page to be displayed
     * @param savedInstanceState page info
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.group_rating_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        navigationService.hideMenuItems(menu, userIntentService.getUserFromIntent(this.getIntent()));

//        searchResult = (TextView) findViewById(R.id.search_result);

        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refresh.setColorSchemeColors(ContextCompat.getColor(GroupRating.this,
                R.color.colorGold), ContextCompat.getColor(GroupRating.this, R.color.colorCardinal));
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (searchResult.getVisibility() != View.VISIBLE) {
//                    getGroupsList();
//                }
//                else {
//                    refresh.setRefreshing(false);
//                }
//            }
//        });
        getGroupsList();

        /* display list of ride offers on screen */
        i = this.getIntent();
        ListView listView = (ListView) findViewById(R.id.group_list);
        adapter = new ArrayAdapter<>(GroupRating.this, android.R.layout.simple_list_item_1, groupMembers);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                /* notify fragment manager to display ride offer information */
//                RideFragment viewOffer = new ViewOffer();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                viewOffer.setData(groups.get(position));
//                viewOffer.setContext(GroupRating.this);
//                viewOffer.setUserInfo(userIntentService.getUserFromIntent(i));
//                fragmentTransaction.replace(R.id.group_rating_activity, viewOffer);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        if (navigationService.checkInternetConnection(GroupRating.this)) {
            connectionPopUp();
        }
    }

    /**
     * Method which notifies ride offer volley to pull ride offer data from database
     */
    @SuppressWarnings("unchecked")
    public void getGroupsList() {
        final UserInfo userInfo = userIntentService.getUserFromIntent(this.getIntent());
        /* notify offer volley to pull data */
        ExpiredGroupVolleyImpl volley = new ExpiredGroupVolleyImpl(this, new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if (result.get(0) instanceof Group) {
                        groups = (ArrayList<Group>) result;
                    }
                } catch (Exception e) {
                    groups = new ArrayList<>();
                    e.printStackTrace();
                }

                /* display data to user */
                adapter.clear();
                groupMembers.clear();
                ArrayList<Group> yourGroups = userRatingService.getGroupsByUser(userInfo, groups);
                groupMembers = userRatingService.getMembersFromGroups(yourGroups);

                /* stop refreshing page */
                if (refresh.isRefreshing()) {
                    refreshService.stopRefreshing(refresh, adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        volley.execute();
    }

    /**
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
        else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        /* close search results */
        else if (View.VISIBLE == searchResult.getVisibility()) {
            searchResult.setVisibility(View.GONE);
            getGroupsList();
        }
        /* return to main activity */
        else {
            finish();
            i = userIntentService.createIntent(GroupRating.this, MainActivity.class, userIntentService.getUserFromIntent(this.getIntent()));
            startActivity(i);
        }
    }

    /**
     * Method that locks side drawer when fragment open
     * @param enabled - true if drawer is locked
     */
    @Override
    public void lockDrawer(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED;
        drawer.setDrawerLockMode(lockMode);
    }

    /**
     * Initializes options menu
     * @param menu to be built
     * @return true on success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_and_search, menu);
        navigationService.hideAdminButton(menu, userIntentService.getUserFromIntent(this.getIntent()));
        return true;
    }

    /**
     * Method to handle user's menu item selection
     * @param item selected
     * @return true on success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (R.id.my_profile == id) {
            i = userIntentService.createIntent(GroupRating.this, ViewProfile.class, userIntentService.getUserFromIntent(this.getIntent()));
            i.putExtra("caller", "Ride Offers");
            startActivity(i);
        }
//        else if (R.id.search == id) {
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            RideSearch rideSearch = new RideSearch();
//            rideSearch.setData(new SearchCallback() {
//                @Override
//                public void call(Place place) {
//                    String display = "Rides near\n" + place.getName().toString();
//
//                    onBackPressed();
//
////                    if(filterResults(place)) {
////                        searchResult.setText(display);
////                        searchResult.setVisibility(View.VISIBLE);
////                    }
//
//                }
//            });
//
//            fragmentTransaction.replace(R.id.ride_offers_activity, rideSearch);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
        else if(id == R.id.admin_actions) {
            i = userIntentService.createIntent(GroupRating.this, AdminActions.class, userIntentService.getUserFromIntent(this.getIntent()));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * method to handle user's page navigation selection
     * @param item selected
     * @return true on success
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        i = navigationService.getNavigationIntent(item, GroupRating.this, this.getIntent());

        /* check if user wants to log out */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.group_rating_activity);
        drawer.closeDrawer(GravityCompat.START);
        if (R.id.logout == id) {
            AlertDialog.Builder alert = navigationService.logOutButton(GroupRating.this);
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SaveSharedPreference.clearUsernamePassword(GroupRating.this);
                    startActivity(i);
                }
            });
            alert.show();

            return true;
        }
        /* check if user needs to connect to wifi */
        else if (navigationService.checkInternetConnection(GroupRating.this)) {
            connectionPopUp();
            return false;
        }
        /* move to desired page */
        else {
            startActivity(i);
            return true;
        }
    }

    /**
     * insert option to connect to wifi
     */
    public void connectionPopUp() {
        Snackbar snackbar = activityService.setupConnection(GroupRating.this, findViewById(R.id.contacts_activity));
        snackbar.show();
    }

    /**
     * Filters ride offers based on search information
     * @param place - user's search info
     * @return true on success
     */
//    public boolean filterResults(Place place) {
//        List<Offer> filtered = new ArrayList<>();
//        List<String> destinations = new ArrayList<>();
//        LatLng compare = place.getLatLng();
//
//        for(int i = 0; i < offers.size(); i++) {
//            float distance[] = new float[1];
//            LatLng current = offers.get(i).getDestCoordinates();
//
//            Location.distanceBetween(compare.latitude, compare.longitude,
//                    current.latitude, current.longitude, distance);
//
//            /* if distance is less than 15 miles away, add it to filtered destinations list */
//            if(distance[0] <= 1600 * 15) {
//                destinations.add(offers.get(i).getDestination());
//                filtered.add(offers.get(i));
//            }
//        }
//
//        /* if no destinations were found, return false */
//        if(destinations.size() == 0) {
//            Snackbar.make(findViewById(R.id.ride_offers_activity),
//                    "No rides available for this location. You can try making a new request.",
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//
//        /* display the filtered results */
//        adapter.clear();
//        for(int i = 0; i < destinations.size(); i++) {
//            this.destinations.add(destinations.get(i));
//        }
//        this.offers = filtered;
//        adapter.notifyDataSetChanged();
//
//        return true;
//    }

}