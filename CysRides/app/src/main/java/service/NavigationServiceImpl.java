package service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import cysrides.cysrides.Contacts;
import cysrides.cysrides.CreateOffer;
import cysrides.cysrides.CreateRequest;
import cysrides.cysrides.LoginActivity;
import cysrides.cysrides.R;
import cysrides.cysrides.RideOffers;
import cysrides.cysrides.RideRequests;
import cysrides.cysrides.ViewProfile;

public class NavigationServiceImpl extends AppCompatActivity implements NavigationService {

    private Intent i;
    private Context context;

    @Override
    public boolean doNavigationItemSelected(@NonNull MenuItem item, Context c, Intent intent) {
        int id = item.getItemId();
        i = intent;
        context = c;
        switch(id)
        {
            case R.id.profile:
                i = new Intent(context, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.requests:
                i = new Intent(context, RideRequests.class);
                startActivity(i);
                break;
            case R.id.offers:
                i = new Intent(context, RideOffers.class);
                startActivity(i);
                break;
            case R.id.contacts:
                break;
            case R.id.createOffer:
                i = new Intent(context, CreateOffer.class);
                startActivity(i);
                break;
            case R.id.createRequest:
                i = new Intent(context, CreateRequest.class);
                startActivity(i);
                break;
            case R.id.logout:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        i = new Intent(context, LoginActivity.class);
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
