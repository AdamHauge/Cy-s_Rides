package service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cysrides.cysrides.CalendarActivity;
import cysrides.cysrides.CreateOffer;
import cysrides.cysrides.CreateRequest;
import cysrides.cysrides.GroupRating;
import cysrides.cysrides.Groups;
import cysrides.cysrides.LoginActivity;
import cysrides.cysrides.Messaging;
import cysrides.cysrides.R;
import cysrides.cysrides.RateRider;
import cysrides.cysrides.RequestsOffers;
import cysrides.cysrides.RideOffers;
import cysrides.cysrides.RideRequests;
import cysrides.cysrides.ViewProfile;
import domain.UserInfo;
import domain.UserType;

public class NavigationServiceImpl extends AppCompatActivity implements NavigationService {

    private UserIntentService userIntentService = new UserIntentServiceImpl();

    //Sets the intent when traversing through pages

    /**
     * This method returns and intent based off where the user is going
     * @param item
     * @param context
     * @param intent
     * @return the new Intent
     */
    @Override
    public Intent getNavigationIntent(@NonNull MenuItem item, Context context, Intent intent) {
        int id = item.getItemId();
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
            case R.id.createOffer:
                intent = userIntentService.createIntent(context, CreateOffer.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.createRequest:
                intent = userIntentService.createIntent(context, CreateRequest.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.requestsOffers:
                intent = userIntentService.createIntent(context, RequestsOffers.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.logout:
                intent = userIntentService.createIntent(context, LoginActivity.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.calendar_activity:
                intent = userIntentService.createIntent(context, CalendarActivity.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.groups:
                intent = userIntentService.createIntent(context, Groups.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.messaging:
                intent = userIntentService.createIntent(context, Messaging.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.group_rating_activity:
                intent = userIntentService.createIntent(context, GroupRating.class, userIntentService.getUserFromIntent(intent));
                break;
            case R.id.rate_rider_activity:
                intent = userIntentService.createIntent(context, RateRider.class, userIntentService.getUserFromIntent(intent));
                break;
            default:
                break;
        }

        return intent;
    }

    /**
     * Checks if the user is connected to the internet
     * @param c
     * @return a boolean
     */
    @Override
    public boolean checkInternetConnection(Context c) {
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return null == networkInfo;
    }

    /**
     * Hides menus from passengers that they shouldn't be able to use
     * @param menu
     * @param userInfo
     */
    @Override
    public void hideMenuItems(Menu menu, UserInfo userInfo) {
        MenuItem item;
        if(userInfo.getUserType() == UserType.PASSENGER) {
            item = menu.findItem(R.id.createOffer);
            item.setVisible(false);
            item = menu.findItem(R.id.requests);
            item.setVisible(false);
        }
    }

    /**
     * Hides the admin button from drivers and passengers
     * @param menu
     * @param userInfo
     */
    @Override
    public void hideAdminButton(Menu menu, UserInfo userInfo) {
        MenuItem item;
        if(userInfo.getUserType() != UserType.ADMIN) {
            item = menu.findItem(R.id.admin_actions);
            item.setVisible(false);
        }
    }

    /**
     * Creates a button to warn the user that they're logging out
     * @param c
     * @return
     */
    @Override
    public AlertDialog.Builder logOutButton(Context c) {
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle("Logout");
        alert.setMessage("Do you really want to logout?");
        alert.setNegativeButton(android.R.string.no, null);
        return alert;
    }
}
