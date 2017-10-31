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

    public void getUsers() {
        UserVolleyImpl volley = new UserVolleyImpl(new Callback() {
            public void call(ArrayList<?> result) {
                try {
                    if(result.get(0) instanceof UserInfo) {
                        users = (ArrayList<UserInfo>) result;
                    }
                } catch(Exception e) {
                    users = new ArrayList<>();
                }

                getUserInfo(username);
            }
        });
        volley.execute();
    }

    private void getUserInfo(String netID) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getNetID().equals(netID)) {
                    user = users.get(i);
                    if(verifyLogin(user.getNetID(), user.getPassword())){
                        loginVerifiedFlag = true;
                    }
                }
            }
        }
        else{

        }
    }

}
