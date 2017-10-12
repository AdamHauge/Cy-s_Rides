package service;

import android.content.Context;

import java.util.List;

import domain.Offer;
import domain.UserInfo;
import domain.UserType;
import volley.OfferVolley;
import volley.OfferVolleyImpl;

public class OfferServiceImpl implements OfferService {

    OfferVolley offerVolley = new OfferVolleyImpl();

    @Override
    public void createOffer(Context context, Offer offer) {
        offerVolley.createOffer(context, offer);
    }

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
