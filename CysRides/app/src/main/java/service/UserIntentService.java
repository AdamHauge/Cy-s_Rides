package service;

import android.content.Context;
import android.content.Intent;

import domain.UserInfo;
import domain.UserType;

public interface UserIntentService {
    Intent createIntent(Context context, Class<?> cls, UserInfo userInfo);
    UserInfo getUserFromIntent(Intent intent);
}
