package service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import domain.UserInfo;

public interface NavigationService {
    Intent getNavigationIntent(@NonNull MenuItem item, Context c, Intent intent);
    boolean checkInternetConnection(Context c);
    void hideMenuItems(Menu menu, UserInfo userInfo);
    void hideAdminButton(Menu menu, UserInfo userInfo);
    AlertDialog.Builder logOutButton(Context c);
}
