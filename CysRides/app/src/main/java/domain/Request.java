package domain;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Request extends Ride {

    private int numBags;

    public Request() {

    }

    //constructs new Request
    public Request(int numBags, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date) {
        super(email, destination, destCoordinates, start, startCoordinates, description, date, "REQUEST");
        this.numBags = numBags;
    }

    //constructor for pulling requests from the database
    public Request(int numBags, String email, String destination, LatLng destCoordinates, String start, LatLng startCoordinates, String description, Date date, int groupID, Context context) {
        super(email, destination, destCoordinates, start, startCoordinates, description, date, groupID, context);
        this.numBags = numBags;

    }

    @Override
    public String toString() {
        return "num bags=" + numBags +
                "\nemail=" + super.getEmail() +
                "\ndescription=" + super.getDescription() +
                "\ndate=" + super.getDate();
    }


    public int getNumBags() {
        return numBags;
    }

    public void setNumBags(int numBags) {
        this.numBags = numBags;
    }
    /*The way this method is set up it will crash the app, if you want to use it you need to change the way you set the intent by passing the user into it.
    * This can be done with the UserIntentService createIntent*/
//    public void viewRequest(Request r, Activity a){
//
//        Intent i = new Intent(a , ViewRequest.class);
//        Bundle b = new Bundle();
////        i.putExtra("UserName", r.getUser().getFirstName() + " " + r.getUser().getLastName());
//        i.putExtra("Dest", r.getDestination());
//        i.putExtra("NumBags", Integer.toString(r.numBags));
//        i.putExtra("Date", r.getDate());
//        i.putExtra("Description", r.getDescription());
//
//        a.startActivity(i);
//    }
}
