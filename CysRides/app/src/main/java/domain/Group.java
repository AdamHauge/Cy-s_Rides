package domain;

import java.util.ArrayList;

/**
 * Created by Ryan on 10/17/2017.
 */

public class Group {
    private ArrayList<String> groupMembers = new ArrayList<>();
    private String driver;
    private int offerID;
    private int groupID;


    public Group(String user){
        addUser(user);
        driver = user;
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


    public ArrayList<String> getGroupMembers(){
        return groupMembers;
    }


    public int getId(){return groupID;}
    public int getOfferId(){return offerID;}


    public void setOfferID(int id){this.offerID = id;}

    public int getSize(){return this.getGroupMembers().size();}



}
