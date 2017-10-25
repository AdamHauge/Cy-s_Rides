package service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;

public interface NavigationService {
    boolean doNavigationItemSelected(@NonNull MenuItem item, Context c, Intent intent);
}
