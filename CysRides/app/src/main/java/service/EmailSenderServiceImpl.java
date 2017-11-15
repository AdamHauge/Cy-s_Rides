package service;

import android.content.Context;

import domain.UserInfo;
import volley.EmailVolley;
import volley.EmailVolleyImpl;

public class EmailSenderServiceImpl implements EmailSenderService {

    public void sendEmail(UserInfo user, Context currentContext)
    {
        EmailVolley emailVolley = new EmailVolleyImpl();
        emailVolley.sendEmail(user.getNetID(), "cysrides@iastate.edu", "Welcome to Cy's Rides!",
                ("Here's your confimation code: " + user.getConfirmationCode()), currentContext);

    }

}