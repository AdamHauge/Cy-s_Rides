package domain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cysrides.cysrides.ViewOffer;
import cysrides.cysrides.ViewRequest;

public class Offer {

    private UserType userType;
    private double cost;
    private UserInfo user;
    private String destination;
    private String description;
    private String date;

    public Offer(UserType userType, double cost, UserInfo user, String destination, String description, String date) {
        this.userType = userType;
        this.cost = cost;
        this.user = user;
        this.destination = destination;
        this.description = description;
        this.date = date;
    }

    //TODO Check if it prints correctly
    @Override
    public String toString() {
        return "Offer{" +
                "userType=" + userType +
                ", cost=" + cost +
                ", user=" + user +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public void setDate(String date) {this.date = date;}

    public String getDate(){return date;}

    public void viewOffer(Offer o, Activity c){

        Intent i = new Intent(c , ViewOffer.class);
        i.putExtra("UserName", o.getUser().getFirstName() + " " + o.getUser().getLastName());
        i.putExtra("Dest", o.getDestination());
        i.putExtra("Cost", Double.toString(o.getCost()));
        i.putExtra("Date", o.getDate());
        i.putExtra("Description", o.getDescription());

        c.startActivity(i);
    }
}
