package domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import service.Callback;
import cysrides.cysrides.ViewRequest;
import volley.GroupVolleyImpl;


public class Request {

    private int numBags;
    private String email;
    private String destination;
    private LatLng coordinates;
    private String description;
    private Date date;
    private Group group;
    private int groupID;
    private Context context;

    //constructs new Request
    public Request(int numBags, String email, String destination, LatLng coordinates, String description, Date date) {
        this.numBags = numBags;
        this.email = email;
        this.destination = destination;
        this.coordinates = coordinates;
        this.description = description;
        this.date = date;

        group = new Group(email, "REQUEST");
    }

    //constructor for pulling requests from the database
    public Request(int numBags, String email, String destination, LatLng coordinates, String description, Date date, int groupID, Context context) {
        this.numBags = numBags;
        this.email = email;
        this.destination = destination;
        this.coordinates = coordinates;
        this.description = description;
        this.date = date;
        this.groupID = groupID;
        this.context = context;

        pullGroup(context, groupID);

    }
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
        return "num bags=" + numBags +
                "\nemail=" + email +
                "\ndescription=" + description +
                "\ndate=" + date;
    }

    public Group getGroup(){return group;}

    public int getNumBags() {
        return numBags;
    }

    public void setNumBags(int numBags) {
        this.numBags = numBags;
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

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
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

    public void viewRequest(Request r, Activity a){

        Intent i = new Intent(a , ViewRequest.class);
        Bundle b = new Bundle();
//        i.putExtra("UserName", r.getUser().getFirstName() + " " + r.getUser().getLastName());
        i.putExtra("Dest", r.getDestination());
        i.putExtra("NumBags", Integer.toString(r.numBags));
        i.putExtra("Date", r.getDate());
        i.putExtra("Description", r.getDescription());

        a.startActivity(i);
    }
}
