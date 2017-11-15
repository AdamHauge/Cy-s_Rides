package service;

import android.content.Context;

import domain.UserInfo;

public interface EmailSenderService {

    public void sendEmail(UserInfo user, Context currentContext);

}
