package volley;

import android.content.Context;
import android.view.View;

import domain.UserInfo;

public interface UserVolley {
    void createUser(Context context, UserInfo user);
    void getUser(Context context, View view, UserInfo user);
}
