package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Offer;
import domain.UserInfo;
import volley.OfferVolley;
import volley.OfferVolleyImpl;

public class OfferServiceImpl implements OfferService {

    OfferVolley offerVolley = new OfferVolleyImpl();

    //Formats the information before passing it into the volley code
    @Override
    public void createOffer(Context context, Offer offer) {
        String latLongName = String.format("%s %s", offer.getDestination(), offer.getCoordinates().toString());
        offerVolley.createOffer(context, offer, latLongName);
    }

    @Override
    public void giveOfferGroup(Context context, final int offerId, final int groupId){
        offerVolley.giveOfferGroup(context, offerId, groupId);
    }

    //Finds the offers for a specific email
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
