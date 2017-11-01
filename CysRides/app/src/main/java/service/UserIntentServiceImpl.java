package service;

import android.content.Context;
import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public class UserIntentServiceImpl implements UserIntentService {

    @Override
    public Intent createIntent(Context context, Class<?> cls, UserInfo user) {
        Intent intent = new Intent(context, cls);
        if (intent.hasExtra("email")) {
            intent.putExtra("email", user.getNetID());
            intent.putExtra("firstName", user.getFirstName());
            intent.putExtra("lastName", user.getLastName());
            intent.putExtra("venmoName", user.getVenmoName());
            intent.putExtra("profileDescription", user.getProfileDescription());
            intent.putExtra("userType", user.getUserType());
            intent.putExtra("userRating", user.getUserRating() + "");
        }
        return intent;
    }

    @Override
    public UserInfo getUserFromIntent(Intent intent) {
        UserInfo userInfo = new UserInfo();
        if (intent.hasExtra("email")) {
            userInfo.setNetID(intent.getExtras().getString("email"));
            userInfo.setFirstName(intent.getExtras().getString("firstName"));
            userInfo.setLastName(intent.getExtras().getString("lastName"));
            userInfo.setVenmoName(intent.getExtras().getString("venmoName"));
            userInfo.setProfileDescription(intent.getExtras().getString("profileDescription"));
            userInfo.setUserType(UserType.valueOf(intent.getExtras().getString("userType")));
            userInfo.setUserRating(Float.valueOf(intent.getExtras().getString("userRating")));
        }

        return userInfo;
    }
}
