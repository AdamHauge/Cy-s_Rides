package service;

import java.util.ArrayList;

import domain.UserInfo;

public interface LoginService {

    boolean verifyLogin(String netID, String password);
    UserInfo getUserInfo(ArrayList<UserInfo> users, String netID, String enteredPassword);
}
