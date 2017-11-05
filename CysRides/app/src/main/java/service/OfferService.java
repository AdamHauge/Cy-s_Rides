package service;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import domain.Offer;
import domain.UserInfo;

public interface OfferService {
    void createOffer(Context context, Offer offer);
    ArrayList<Offer> findOffersByEmail(ArrayList<Offer> offers, UserInfo userInfo);
}
