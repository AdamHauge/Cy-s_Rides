package volley;

import android.content.Context;
import android.view.View;

import java.util.List;

import domain.Offer;
import service.ListenerService;

public interface OfferVolley {
    void createOffer(Context context, Offer offer, String latLongName);
    public void giveOfferGroup(Context context, final int offerId, final int groupId);

}
