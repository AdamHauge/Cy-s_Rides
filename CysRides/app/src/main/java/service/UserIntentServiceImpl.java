package service;

import android.content.Context;
import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public class UserIntentServiceImpl implements UserIntentService {

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
