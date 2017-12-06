package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Offer;
import domain.UserInfo;
import volley.OfferVolley;
import volley.OfferVolleyImpl;

public class OfferServiceImpl implements OfferService {

    private OfferVolley offerVolley = new OfferVolleyImpl();

    //Formats the information before passing it into the volley code

    /**
     * Creates an offer in the database when given an offer
     * @param context
     * @param offer
     */
    @Override
    public void createOffer(Context context, Offer offer) {
        String destination = String.format("%s %s", offer.getDestination(), offer.getDestCoordinates().toString());
        String start = String.format("%s %s", offer.getStart(), offer.getStartCoordinates().toString());
        offerVolley.createOffer(context, offer, destination, start);
    }

    /**
     * Deletes an offer in the database
     * @param context
     * @param id
     */
    @Override
    public void deleteOffer(Context context, int id) {
        offerVolley.deleteOffer(context, id);
    }

    /**
     * Gives an offer from the database from a certain id
     * @param context
     * @param offerId
     * @param groupId
     */
    @Override
    public void giveOfferGroup(Context context, final int offerId, final int groupId){
        offerVolley.giveOfferGroup(context, offerId, groupId);
    }

    /**
     * Finds offers from the database by who created them
     * @param offers
     * @param userInfo
     * @return
     */
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
