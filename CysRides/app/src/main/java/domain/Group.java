package domain;

import java.util.ArrayList;

/**
 * Created by Ryan on 10/17/2017.
 */

public class Group {
    private ArrayList<UserInfo> groupMembers = new ArrayList<>();
    private UserInfo driver;
    private int groupType;

    public Group(UserInfo user){
        addUser(user);
        driver = user;
        //Group corresponding to a trip group
        groupType = 0;
    }

    public Group(ArrayList<UserInfo> contacts){
        groupMembers = contacts;
        //Group corresponding to a chat with contacts
        groupType = 1;
    }

    //returns -1 if group is full
    public int addUser(UserInfo user){
        //return -1 if too many members in a trip group
        if(groupMembers.size() >= 8 && groupType == 0){
            return -1;
        }
        groupMembers.add(user);
        return 1;
    }

    //returns -1 if trying to remove driver
    public int removeUser(UserInfo user){

        //return if trying to remove driver in a trip group
        if(user == driver && groupType == 0){
            return -1;
        }
        groupMembers.remove(user);
        return -1;

    }

    public ArrayList<UserInfo> getGroupMembers(){
        return groupMembers;
    }



}
