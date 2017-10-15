package service;

import java.util.List;

import domain.Offer;

public interface ListenerService {

    void onResponseReceived(List<Offer> offers);

}
