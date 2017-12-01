package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Request;
import domain.UserInfo;
import volley.RequestVolley;
import volley.RequestVolleyImpl;

public class RequestServiceImpl implements RequestService {

    private RequestVolley requestVolley = new RequestVolleyImpl();

    //Formats the information before passing it into the volley code
    @Override
    public void createRequest(Context context, Request request) {
        String destName = String.format("%s %s", request.getDestination(), request.getDestCoordinates().toString());
        String startName = String.format("%s %s", request.getStart(), request.getStartCoordinates().toString());
        requestVolley.createRequest(context, request, destName, startName);
    }

    @Override
    public void deleteRequest(Context context, int id) {
        requestVolley.deleteRequest(context, id);
    }

    //Finds the requests for a specific email
    @Override
    public ArrayList<Request> findRequestsByEmail(ArrayList<Request> requests, UserInfo userInfo) {
        ArrayList<Request> emailRequests = new ArrayList<>();
        for(int i=0 ; i<requests.size() ; i++) {
            if(requests.get(i).getEmail().equals(userInfo.getNetID())) {
                emailRequests.add(requests.get(i));
            }
        }
        return emailRequests;
    }
    @Override
    public void giveRequestGroup(Context context, int requestID, int groupID){
        requestVolley.giveRequestGroup(context, requestID, groupID);
    }

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
