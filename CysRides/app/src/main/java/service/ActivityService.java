package service;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public interface ActivityService {
    Snackbar setupConnection(final Context context, View v);
}
