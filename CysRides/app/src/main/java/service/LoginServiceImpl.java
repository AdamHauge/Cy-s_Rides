package service;

import java.util.ArrayList;

import cysrides.cysrides.Callback;
import domain.UserInfo;
import volley.UserVolleyImpl;

public class LoginServiceImpl implements LoginService {

    private UserInfo user;
    private ArrayList<UserInfo> users;
    private String username;
    private boolean loginVerifiedFlag;
    private String dbUsername;
    private String dbPassword;

    public LoginServiceImpl(){
        loginVerifiedFlag = false;
    }

    @Override
    public boolean verifyLogin(String netID, String password){

        return false;
    }

    public UserInfo getUserInfo(ArrayList<UserInfo> users, String netID, String enteredPassword) {
        UserInfo userInfo;
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getNetID().equals(netID)) {
                    userInfo = users.get(i);
                    for(int j = 0; j < users.size(); j++){
                        if(enteredPassword.equals(userInfo.getPassword())){
                            return userInfo;
                        }
                    }
                }
            }
        }
        return null;
    }

}
