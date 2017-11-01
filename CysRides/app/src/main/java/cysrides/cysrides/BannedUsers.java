package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import domain.Ban;
import domain.Offer;
import service.NavigationService;
import service.NavigationServiceImpl;
import volley.BanVolley;
import volley.BanVolleyImpl;
import volley.OfferVolleyImpl;

public class BannedUsers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationService navigationService = new NavigationServiceImpl();

    private ArrayAdapter<String> adapter;
    private List<Ban> bans = new ArrayList<>();
    FragmentManager fragmentManager = this.getSupportFragmentManager();
    private Intent i;

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

        getBansList();
//TODO
        ListView listView = (ListView)findViewById(R.id.ride_offers_list);
//        adapter = new ArrayAdapter<>(BannedUsers.this, android.R.layout.simple_list_item_1, destinations);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                ViewOffer viewOffer = new ViewOffer();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                viewOffer.setData(offers.get(position));
//
//                fragmentTransaction.replace(R.id.ride_offers_activity, viewOffer);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        if(navigationService.checkInternetConnection(getApplicationContext())) {
            connectionPopUp();
        }
    }

    @SuppressWarnings("unchecked")
    public void getBansList() {
        BanVolleyImpl volley = new BanVolleyImpl(new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if (result.get(0) instanceof Ban) {
                        bans = (ArrayList<Ban>) result;
                    }
                } catch(Exception e) {
                    bans = new ArrayList<>();
                }

                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        });
        volley.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ride_offers_activity);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else {
            finish();
            i = new Intent(BannedUsers.this, MainActivity.class);
            startActivity(i);
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
            i = new Intent(BannedUsers.this, ViewProfile.class);
            i.putExtra("caller", "Ride Offers");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        i = navigationService.getNavigationIntent(item, BannedUsers.this, i);

        if(R.id.logout == id) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Logout");
            alert.setMessage("Do you really want to logout?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    startActivity(i);
                }});
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.banned_users_activity);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if(navigationService.checkInternetConnection(getApplicationContext())) {
            connectionPopUp();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.banned_users_activity);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.banned_users_activity);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(i);
            return true;
        }
    }

    public void connectionPopUp() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.ride_offers_activity),
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
