package service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import cysrides.cysrides.Contacts;
import cysrides.cysrides.CreateOffer;
import cysrides.cysrides.CreateRequest;
import cysrides.cysrides.LoginActivity;
import cysrides.cysrides.R;
import cysrides.cysrides.RideOffers;
import cysrides.cysrides.RideRequests;
import cysrides.cysrides.ViewProfile;

public class NavigationServiceImpl implements NavigationService {

    private UserIntentService userIntentService = new UserIntentServiceImpl();

    private Intent intent;
    private Context context;

    @Override
    public Intent getNavigationIntent(@NonNull MenuItem item, Context c, Intent i) {
        int id = item.getItemId();
        intent = i;
        context = c;
        switch(id)
        {
            case R.id.profile:
                intent = userIntentService.createIntent(context, ViewProfile.class, "zgknoll@iastate.edu");
                break;
            case R.id.requests:
                intent = userIntentService.createIntent(context, RideRequests.class, "zgknoll@iastate.edu");
                break;
            case R.id.offers:
                intent = userIntentService.createIntent(context, RideOffers.class, "zgknoll@iastate.edu");
                break;
            case R.id.contacts:
                intent = userIntentService.createIntent(context, Contacts.class, "zgknoll@iastate.edu");
                break;
            case R.id.createOffer:
                intent = userIntentService.createIntent(context, CreateOffer.class, "zgknoll@iastate.edu");
                break;
            case R.id.createRequest:
                intent = userIntentService.createIntent(context, CreateRequest.class, "zgknoll@iastate.edu");
                break;
            case R.id.logout:
                intent = userIntentService.createIntent(context, LoginActivity.class, "zgknoll@iastate.edu");
                break;
            default:
                break;
        }

        return intent;
    }

    @Override
    public boolean checkInternetConnection(Context c) {
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return null == networkInfo;
    }
}
