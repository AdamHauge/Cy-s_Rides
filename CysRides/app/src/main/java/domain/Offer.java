package domain;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import service.Callback;
import volley.GroupVolleyImpl;

public class Offer {

    private double cost;
    private String email;
    private String destination;
    private String start;
    private LatLng destCoordinates;
    private LatLng startCoordinates;
    private String description;
    private Date date;
    private Group group;
    private int groupID;

    //create offer
    public Offer(double cost, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date) {
        this.cost = cost;
        this.email = email;
        this.destination = destination;
        this.destCoordinates = destCoordinates;
        this.start = start;
        this.startCoordinates = startCoordinates;
        this.description = description;
        this.date = date;

        group = new Group(email, "OFFER");

    }
    //constructer used to pull offers
    public Offer(double cost, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        this.cost = cost;
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

    public Offer() {

    }
    //pulls group from the database and sets this.group to the fetched group
    public void pullGroup(Context context, int groupID){
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

    @Override
    public String toString() {
        return  "destination=" + destination +
                "\nstart=" + start +
                "\ncost=$" + cost +
                "\nemail=" + email +
                "\ndescription=" + description +
                "\ndate=" + date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public void setStart(String destination) {
        this.destination = start;
    }

    public LatLng getDestCoordinates() { return destCoordinates; }

    public void setDestCoordinates(LatLng coordinates) { this.destCoordinates = coordinates; }

    public LatLng getStartCoordinates() { return startCoordinates; }

    public void setStartCoordinates(LatLng coordinates) { this.destCoordinates = coordinates; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {this.date = date;}

    public Date getDate(){return date;}

    public Group getGroup(){return group;}

}
