package domain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

import cysrides.cysrides.ViewOffer;
import cysrides.cysrides.ViewRequest;

public class Offer {

    private double cost;
    private String email;
    private String destination;
    private String description;
    private Date date;

    public Offer(UserType user, double cost, String email, String destination, String description, Date date) {
        this.cost = cost;
        this.email = email;
        this.destination = destination;
        this.description = description;
        this.date = date;
    }

    public Offer() {

    }

    //TODO Check if it prints correctly I changed date to a Date instead of a String and got rid of usertype, my b
    @Override
    public String toString() {
        return "Offer{" +
                ", cost=" + cost +
                ", email=" + email +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {this.date = date;}

    public Date getDate(){return date;}

    /*Sorry for messing with your code, but I didn't think about the database when designing this Object,
    and it would make the database a lot more complicated than it needed to be to have a UserInfo
    Object in the Offer Object, so I changed it to email. We can get the first and last name by passing
    the email through the database later.*/
    public void viewOffer(Offer o, Activity c){

        Intent i = new Intent(c , ViewOffer.class);
//        i.putExtra("UserName", o.getUser().getFirstName() + " " + o.getUser().getLastName());
        i.putExtra("Email", o.getEmail());
        i.putExtra("Dest", o.getDestination());
        i.putExtra("Cost", Double.toString(o.getCost()));
        i.putExtra("Date", o.getDate());
        i.putExtra("Description", o.getDescription());

        c.startActivity(i);
    }
}
