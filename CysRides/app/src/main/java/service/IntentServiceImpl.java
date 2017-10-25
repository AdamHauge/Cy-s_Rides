package service;

import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public class IntentServiceImpl {

    public Intent createIntent(String netId, String firstName, String lastName, String venmoName, String profileDescription, UserType userType, float userRating) {
        Intent intent = new Intent(/*this, Activity.class*/);
        intent.putExtra("email", netId);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("venmoName", venmoName);
        intent.putExtra("profileDescription", profileDescription);
        intent.putExtra("userType", userType.toString());
        intent.putExtra("userRating", userRating+"");
        return null;
    }

    public UserInfo getUserFromIntent(Intent intent) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNetID(intent.getExtras().getString("email"));
        userInfo.setFirstName(intent.getExtras().getString("firstName"));
        userInfo.setLastName(intent.getExtras().getString("lastName"));
        userInfo.setVenmoName(intent.getExtras().getString("venmoName"));
        userInfo.setProfileDescription(intent.getExtras().getString("profileDescription"));
        userInfo.setUserType(UserType.valueOf(intent.getExtras().getString("userType")));
        userInfo.setUserRating(Float.valueOf(intent.getExtras().getString("userRating")));
        return userInfo;
    }
}
