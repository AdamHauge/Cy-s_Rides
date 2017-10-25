package service;

import android.content.Context;
import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public interface UserIntentService {
    Intent createIntent(Context context, Class<?> cls, String netId/*, String lastName, String venmoName, String profileDescription, UserType userType, float userRating*/);
    UserInfo getUserFromIntent(Intent intent);
}
