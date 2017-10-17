package cysrides.cysrides;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import domain.Offer;
import volley.OfferVolleyImpl;

public class RideOffers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<String> adapter;
    private List<Offer> offers = new ArrayList<>();
    private List<String> destinations = new ArrayList<>();
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getOffersList();

        ListView listView = (ListView)findViewById(R.id.ride_offers_list);
        adapter = new ArrayAdapter<>(RideOffers.this, android.R.layout.simple_list_item_1, destinations);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RideOffers.this);
                alert.setTitle("Offer Info");
                alert.setMessage(offers.get(position).toString());
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        });
    }

    public void getOffersList() {
        OfferVolleyImpl volley = new OfferVolleyImpl(new Callback() {
            public void call(ArrayList<Offer> result) {
                Log.d("Array", result.toString());
                offers = result;
                adapter.clear();
                destinations.clear();
                for(int i = 0; i < offers.size(); i++) {
                    destinations.add(offers.get(i).getDestination());
                }
                adapter.notifyDataSetChanged();
            }
        });
        volley.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            i = new Intent(RideOffers.this, MainActivity.class);
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
            i = new Intent(RideOffers.this, ViewProfile.class);
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

        switch(id)
        {
            case R.id.profile:
                i = new Intent(RideOffers.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.requests:
                i = new Intent(RideOffers.this, RideRequests.class);
                startActivity(i);
                break;
            case R.id.offers:
                break;
            case R.id.contacts:
                i = new Intent(RideOffers.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.createOffer:
                i = new Intent(RideOffers.this, CreateOffer.class);
                startActivity(i);
                break;
            case R.id.createRequest:
                i = new Intent(RideOffers.this, CreateRequest.class);
                startActivity(i);
                break;
            case R.id.logout:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        i = new Intent(RideOffers.this, LoginActivity.class);
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