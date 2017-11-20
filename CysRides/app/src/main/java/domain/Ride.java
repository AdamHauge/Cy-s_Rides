package domain;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import service.Callback;
import volley.GroupVolleyImpl;

public class Ride {

    private int id;
    private String email;
    private String destination;
    private String start;
    private LatLng destCoordinates;
    private LatLng startCoordinates;
    private String description;
    private Date date;
    private Group group;
    private int groupID;

    public Ride(){

    }

    public Ride(String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, String type) {
        this.email = email;
        this.destination = destination;
        this.destCoordinates = destCoordinates;
        this.start = start;
        this.startCoordinates = startCoordinates;
        this.description = description;
        this.date = date;

        group = new Group(email, type);
    }

    public Ride(int id, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, String type) {
        this.id = id;
        this.email = email;
        this.destination = destination;
        this.destCoordinates = destCoordinates;
        this.start = start;
        this.startCoordinates = startCoordinates;
        this.description = description;
        this.date = date;

        group = new Group(email, type);
    }

    public Ride(String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        this.email = email;
        this.destination = destination;
        this.destCoordinates = destCoordinates;
        this.description = description;
        this.start = start;
        this.startCoordinates = startCoordinates;
        this.date = date;
        this.groupID = groupID;
        //USE CALLBACK SOMEHOW TO GET THE GROUP BACK
        pullGroup(context, this.groupID);
    }

    public Ride(int id, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        this.id = id;
        this.email = email;
        this.destination = destination;
        this.destCoordinates = destCoordinates;
        this.description = description;
        this.start = start;
        this.startCoordinates = startCoordinates;
        this.date = date;
        this.groupID = groupID;
        //USE CALLBACK SOMEHOW TO GET THE GROUP BACK
        pullGroup(context, this.groupID);
    }

    private void pullGroup(Context context, int groupID){
        GroupVolleyImpl gvi = new GroupVolleyImpl(context, new Callback() {
            @Override
            public void call(ArrayList<?> result) {
                try{
                    if(result.get(0) instanceof  Group){
                        group = (Group) result.get(0);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        gvi.getGroup(context, groupID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public LatLng getDestCoordinates() {
        return destCoordinates;
    }

    public void setDestCoordinates(LatLng destCoordinates) {
        this.destCoordinates = destCoordinates;
    }

    public LatLng getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(LatLng startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
