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

    private Intent intent;
    private Context context;
    private MenuItem item;

    @Override
    public Intent getNavigationIntent(@NonNull MenuItem item, Context c, Intent i) {
        int id = item.getItemId();
        intent = i;
        context = c;
        switch(id)
        {
            case R.id.profile:
                intent = new Intent(context, ViewProfile.class);
                break;
            case R.id.requests:
                intent = new Intent(context, RideRequests.class);
                break;
            case R.id.offers:
                intent = new Intent(context, RideOffers.class);
                break;
            case R.id.contacts:
                intent = new Intent(context, RideOffers.class);
                break;
            case R.id.createOffer:
                intent = new Intent(context, CreateOffer.class);
                break;
            case R.id.createRequest:
                intent = new Intent(context, CreateRequest.class);
                break;
            case R.id.logout:
                intent = new Intent(context, LoginActivity.class);
                break;
            default:
                break;
        }
        return intent;
    }
}
