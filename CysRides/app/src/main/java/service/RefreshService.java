package service;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

public interface RefreshService {
    void stopRefreshing(SwipeRefreshLayout r, ArrayAdapter<String> a);
}
