package domain;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import service.Callback;
import volley.GroupVolleyImpl;

public class Offer extends Ride {

    private double cost;

    public Offer() {

    }

    //create offer
    public Offer(double cost, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date) {
        super(email, destination, destCoordinates, start, startCoordinates, description, date, "OFFER");
        this.cost = cost;
    }

    //constructer used to pull offers
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
