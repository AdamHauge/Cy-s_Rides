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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import cysrides.cysrides.BanUser;
import cysrides.cysrides.BannedUsers;
import cysrides.cysrides.Contacts;
import cysrides.cysrides.CreateOffer;
import cysrides.cysrides.CreateRequest;
import cysrides.cysrides.LoginActivity;
import cysrides.cysrides.R;
import cysrides.cysrides.RequestsOffers;
import cysrides.cysrides.RideOffers;
import cysrides.cysrides.RideRequests;
import cysrides.cysrides.ViewProfile;
import domain.UserInfo;
import domain.UserType;

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
                intent = userIntentService.createIntent(context, ViewProfile.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.requests:
                intent = userIntentService.createIntent(context, RideRequests.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.offers:
                intent = userIntentService.createIntent(context, RideOffers.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.contacts:
                intent = userIntentService.createIntent(context, Contacts.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.createOffer:
                intent = userIntentService.createIntent(context, CreateOffer.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.createRequest:
                intent = userIntentService.createIntent(context, CreateRequest.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.requestsOffers:
                intent = userIntentService.createIntent(context, RequestsOffers.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.bannedUsers:
                intent = userIntentService.createIntent(context, BannedUsers.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.banUser:
                intent = userIntentService.createIntent(context, BanUser.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.logout:
                intent = userIntentService.createIntent(context, LoginActivity.class, userIntentService.getUserFromIntent(intent));
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

    @Override
    public void hideMenuItems(Menu menu, UserInfo userInfo) {
        MenuItem item;
        if(userInfo.getUserType() == UserType.PASSENGER) {
            item = menu.findItem(R.id.createOffer);
            item.setVisible(false);
            item = menu.findItem(R.id.requests);
            item.setVisible(false);
            item = menu.findItem(R.id.bannedUsers);
            item.setVisible(false);
            item = menu.findItem(R.id.banUser);
            item.setVisible(false);
        } else if(userInfo.getUserType() == UserType.DRIVER) {
            item = menu.findItem(R.id.bannedUsers);
            item.setVisible(false);
            item = menu.findItem(R.id.banUser);
            item.setVisible(false);
        }
    }
}
