package service;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        String latLongName = String.format("%s %s", offer.getDestination(), offer.getCoordinates().toString());
        offerVolley.createOffer(context, offer, latLongName);
    }

    @Override
    public void giveOfferGroup(Context context, final int offerId, final int groupId){
        offerVolley.giveOfferGroup(context, offerId, groupId);
    }

    @Override
    public ArrayList<Offer> findOffersByEmail(ArrayList<Offer> offers, UserInfo userInfo) {
        ArrayList<Offer> emailOffers = new ArrayList<>();
        for(int i=0 ; i<offers.size() ; i++) {
            if(offers.get(i).getEmail().equals(userInfo.getNetID())) {
                emailOffers.add(offers.get(i));
            }
        }
        return emailOffers;
    }

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
