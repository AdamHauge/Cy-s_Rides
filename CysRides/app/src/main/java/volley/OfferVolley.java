package volley;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import java.util.List;

import domain.Offer;

public interface OfferVolley {
    void createOffer(Context context, View view, Offer offer);
    List<Offer> getOffers();
}
