package volley;

import android.content.Context;

import domain.UserInfo;

public interface UserVolley {
    void createUser(Context context, UserInfo user);
}
