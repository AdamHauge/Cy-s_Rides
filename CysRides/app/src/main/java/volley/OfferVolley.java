package volley;

import android.content.Context;

import domain.Offer;

public interface OfferVolley {
    void createOffer(Context context, Offer offer, String latLongName);
    void giveOfferGroup(Context context, final int offerId, final int groupId);

}
