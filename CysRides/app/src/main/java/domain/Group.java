package domain;

import java.util.ArrayList;

import service.GroupServiceImpl;
import volley.GroupVolleyImpl;

/**
 * Created by Ryan on 10/17/2017.
 */

public class Group {
    private ArrayList<String> groupMembers = new ArrayList<>();
    private String driver;
    private int offerID;
    private int groupID;


    public Group(String user){
        groupMembers.add(user);
        driver = user;
    }

    public Group(int groupID, ArrayList<String> groupMembers, int offerID){
        this.groupMembers = groupMembers;
        this.groupID = groupID;
        this.offerID = offerID;
        this.driver = groupMembers.get(0);

    }



    public ArrayList<String> getGroupMembers(){
        return groupMembers;
    }

    public String getDriver(){return driver;}

    public int getId(){return groupID;}

    public int getOfferId(){return offerID;}


    public void setOfferID(int id){this.offerID = id;}

    public int getSize(){return this.getGroupMembers().size();}



}
