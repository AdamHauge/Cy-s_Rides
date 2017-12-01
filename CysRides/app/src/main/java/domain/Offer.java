package domain;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Offer extends Ride {

    private double cost;

    public Offer() {

    }

    //create offer
    public Offer(double cost, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date) {
        super(email, destination, destCoordinates, start, startCoordinates, description, date, "OFFER");
        this.cost = cost;
    }

    public Offer(double cost, int id, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date) {
        super(id, email, destination, destCoordinates, start, startCoordinates, description, date, "OFFER");
        this.cost = cost;
    }

    //constructer used to pull offers
    public Offer(double cost, int id, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        super(id, email, destination, destCoordinates, start, startCoordinates, description, date, groupID, context);
        this.cost = cost;
    }

    public Offer(double cost, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        super(email, destination, destCoordinates, start, startCoordinates, description, date, groupID, context);
        this.cost = cost;
    }

    @Override
    public String toString() {
        return  "destination=" + super.getDestination() +
                "\nstart=" + super.getStart() +
                "\ncost=$" + cost +
                "\nemail=" + super.getEmail() +
                "\ndescription=" + super.getDescription() +
                "\ndate=" + super.getDate();
    }

    public String adminOffer() {
        return  "id=" + getId() +
                "\ndestination=" + getDestination() +
                "\nstart=" + getStart() +
                "\ncost=$" + getCost() +
                "\nemail=" + getEmail() +
                "\ndescription=" + getDescription() +
                "\ndate=" + getDate();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
