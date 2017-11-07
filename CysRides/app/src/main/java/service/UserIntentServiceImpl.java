package service;

import android.content.Context;
import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public class UserIntentServiceImpl implements UserIntentService {

    /*
    Creates an intent based on the current context and class. A user is used as
    a parameter so that it can break down all relevant parts of userInfo and send
    the data across the intent.
     */
    @Override
    public Intent createIntent(Context context, Class<?> cls, UserInfo userInfo) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("email", userInfo.getNetID());
        intent.putExtra("confirmation code", userInfo.getConfirmationCode());
        intent.putExtra("firstName", userInfo.getFirstName());
        intent.putExtra("lastName", userInfo.getLastName());
        intent.putExtra("venmoName", userInfo.getVenmoName());
        intent.putExtra("profileDescription", userInfo.getProfileDescription());
        intent.putExtra("userType", userInfo.getUserType().toString());
        intent.putExtra("userRating", userInfo.getUserRating() + "");
        return intent;
    }

    /*
    Grabs user from the intent that was used. Creates UserInfo object and sets its fields
    to the values of the strings send over from the CreateIntent method.
     */
    @Override
    public UserInfo getUserFromIntent(Intent intent) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNetID(intent.getExtras().getString("email"));
        userInfo.setConfirmationCode(intent.getExtras().getString("confirmation code"));
        userInfo.setFirstName(intent.getExtras().getString("firstName"));
        userInfo.setLastName(intent.getExtras().getString("lastName"));
        userInfo.setVenmoName(intent.getExtras().getString("venmoName"));
        userInfo.setProfileDescription(intent.getExtras().getString("profileDescription"));
        userInfo.setUserType(UserType.valueOf(intent.getExtras().getString("userType")));
        userInfo.setUserRating(Float.valueOf(intent.getExtras().getString("userRating")));
        return userInfo;
    }
}
