package volley;

import android.content.Context;
import android.view.View;

import java.util.List;

import domain.Offer;

public interface OfferVolley {
    void createOffer(Context context, Offer offer);
    List<Offer> getOffers(Context context);
}
