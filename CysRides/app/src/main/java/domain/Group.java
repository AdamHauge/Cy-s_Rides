package domain;

import java.util.ArrayList;

/**
 * Created by Ryan on 10/17/2017.
 */

public class Group {
    private ArrayList<String> groupMembers = new ArrayList<>();
    private String driver;
    private int groupType;
    private int groupID;

    public Group(String user){
        addUser(user);
        driver = user;
    }

    public Group(ArrayList<String> contacts){
        groupMembers = contacts;

    }

    //returns -1 if group is full
    public int addUser(String user){
        //return -1 if too many members in a trip group
        if(groupMembers.size() >= 8){
            return -1;
        }
        groupMembers.add(user);
        return 1;
    }

    //returns -1 if trying to remove driver
    public int removeUser(String user){

        //return if trying to remove driver in a trip group
        if(user.equals(driver) && groupType == 0){
            return -1;
        }
        groupMembers.remove(user);
        return -1;

    }

    public ArrayList<String> getGroupMembers(){
        return groupMembers;
    }

    public int getId(){return groupID;}



}
