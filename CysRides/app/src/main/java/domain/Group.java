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
    private int requestID;
    private String type;

    public Group(String user, String type){
        this.type = type;
        if(type.equals("OFFER")) {
            groupMembers.add(user);
            driver = user;
        }
        if(type.equals("REQUEST")){
            groupMembers.add(null);
            groupMembers.add(user);
        }
    }

    public Group(int groupID, ArrayList<String> groupMembers, int offerID, int requestID){
        this.groupMembers = groupMembers;
        this.groupID = groupID;
        this.offerID = offerID;
        this.requestID = requestID;
        this.driver = groupMembers.get(0);

    }



    public ArrayList<String> getGroupMembers(){
        return groupMembers;
    }

    public String getType(){return type;}

    public String getDriver(){return driver;}

    public int getId(){return groupID;}

    public int getOfferId(){return offerID;}

    public void setOfferID(int id){this.offerID = id;}

    public void setRequestID(int id){this.requestID = id;}

    public int getRequestID(){return this.requestID;}

    public int getSize(){return this.getGroupMembers().size();}



}
