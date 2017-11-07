package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Request;
import domain.UserInfo;
import volley.RequestVolley;
import volley.RequestVolleyImpl;

public class RequestServiceImpl implements RequestService {

    RequestVolley requestVolley = new RequestVolleyImpl();

    @Override
    public void createRequest(Context context, Request request) {
        String latLongName = String.format("%s %s", request.getDestination(), request.getCoordinates().toString());
        requestVolley.createRequest(context, request, latLongName);
    }

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

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
