package jdbc;

import java.util.List;

import domain.Offer;

public interface OfferJdbc {

    List<Offer> getDriverOffers();
    List<Offer> getPassengerOffers();
}
