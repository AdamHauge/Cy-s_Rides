package domain;

import java.util.ArrayList;

public class Group {
    private ArrayList<String> groupMembers = new ArrayList<>();
    private String driver;
    private int offerID;
    private int groupID;
    private int requestID;
    private String type;

    //Group that is created in offer
    public Group(String user, String type){

        this.type = type;
        //if this is an offer, add the user as a driver
        if(type.equals("OFFER")) {
            groupMembers.add(user);
            driver = user;
        }
        //if this is a request, add a null
        if(type.equals("REQUEST")){
            groupMembers.add(null);
            groupMembers.add(user);
        }

    }
    //Group that is pulled from database
    public Group(int groupID, ArrayList<String> groupMembers, int offerID, int requestID){
        this.groupMembers = groupMembers;
        this.groupID = groupID;
        this.offerID = offerID;
        this.requestID = requestID;
        this.driver = groupMembers.get(0);

        if(this.offerID == Integer.MIN_VALUE){
            this.type = "REQUEST";
        }else{
            this.type = "OFFER";
        }

    }

    public boolean inGroup(String netID) {
        return groupMembers.contains(netID);
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
