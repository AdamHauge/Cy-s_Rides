package service;

import android.content.Context;

import domain.Offer;
import domain.Request;
import volley.OfferVolley;
import volley.OfferVolleyImpl;
import volley.RequestVolley;
import volley.RequestVolleyImpl;

public class RequestServiceImpl implements RequestService {

    RequestVolley requestVolley = new RequestVolleyImpl();

    @Override
    public void createRequest(Context context, Request request) {
        String latLongName = String.format("%s %s", request.getDestination(), request.getCoordinates().toString());
        requestVolley.createRequest(context, request, latLongName);
    }

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
