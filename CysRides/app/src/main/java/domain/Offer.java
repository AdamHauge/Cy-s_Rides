package domain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Offer {

    private double cost;
    private String email;
    private String destination;
    private LatLng coordinates;
    private String description;
    private Date date;
    private Group group;

    public Offer(double cost, String email, String destination, LatLng coordinates, String description, Date date) {
        this.cost = cost;
        this.email = email;
        this.destination = destination;
        this.coordinates = coordinates;
        this.description = description;
        this.date = date;

        group = new Group("DRIVER");

    }

    public Offer() {

    }

    @Override
    public String toString() {
        return "cost=$" + cost +
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

    public LatLng getCoordinates() { return coordinates; }

    public void setCoordinates(LatLng coordinates) { this.coordinates = coordinates; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {this.date = date;}

    public Date getDate(){return date;}

    public Group getGroup(){return group;}

    public void viewOffer(Offer o, Activity c){

//        Intent i = new Intent(c , ViewOffer.class);
//        i.putExtra("Email", o.getEmail());
//        i.putExtra("Dest", o.getDestination());
//        i.putExtra("LatLng", o.getCoordinates());
//        i.putExtra("Cost", Double.toString(o.getCost()));
//        i.putExtra("Date", o.getDate());
//        i.putExtra("Description", o.getDescription());
//
//        c.startActivity(i);
    }
}
