package volley;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import domain.UserInfo;

public interface UserVolley {
    void createUser(Context context, UserInfo user);
}
