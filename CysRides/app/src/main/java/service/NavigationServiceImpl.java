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

    private UserIntentService userIntentService = new UserIntentServiceImpl();

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
                intent = userIntentService.createIntent(context, ViewProfile.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, ViewProfile.class);
                break;
            case R.id.requests:
                intent = userIntentService.createIntent(context, RideRequests.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, RideRequests.class);
                break;
            case R.id.offers:
                intent = userIntentService.createIntent(context, RideOffers.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, RideOffers.class);
                break;
            case R.id.contacts:
                intent = userIntentService.createIntent(context, Contacts.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, Contacts.class);
                break;
            case R.id.createOffer:
                intent = userIntentService.createIntent(context, CreateOffer.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, CreateOffer.class);
                break;
            case R.id.createRequest:
                intent = userIntentService.createIntent(context, CreateRequest.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, CreateRequest.class);
                break;
            case R.id.logout:
                intent = userIntentService.createIntent(context, LoginActivity.class, "zgknoll@iastate.edu");
//                intent = new Intent(context, LoginActivity.class);
                break;
            default:
                break;
        }
        return intent;
    }
}
