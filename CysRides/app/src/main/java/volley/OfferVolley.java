package volley;

import android.app.AlertDialog;
import android.content.Context;

import java.util.List;

import domain.Offer;

public interface OfferVolley {
    void createOffer(Context context, Offer offer);
}
