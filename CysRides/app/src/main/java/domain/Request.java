package domain;

/**
 * Created by Ryan on 9/13/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cysrides.cysrides.CreateRequest;
import cysrides.cysrides.ViewRequest;


public class Request {

    private int num_bags;
    private UserInfo user;
    private String destination;
    private String description;
    private String date;

    public Request(int num_bags, UserInfo user, String destination, String description, String date) {
        this.num_bags = num_bags;
        this.user = user;
        this.destination = destination;
        this.description = description;
        this.date = date;
    }



    public double getNumBags() {
        return num_bags;
    }

    public void setNum_bags(int num_bags) {
        this.num_bags = num_bags;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public void viewRequest(Request r, Activity a){

        Intent i = new Intent(a , ViewRequest.class);
        Bundle b = new Bundle();
        i.putExtra("UserName", r.getUser().getFirstName() + " " + r.getUser().getLastName());
        i.putExtra("Dest", r.getDestination());
        i.putExtra("NumBags", Integer.toString(r.num_bags));
        i.putExtra("Date", r.getDate());
        i.putExtra("Description", r.getDescription());

        a.startActivity(i);
    }
}
