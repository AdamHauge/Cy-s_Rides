package service;

import java.util.List;

import domain.Offer;
import domain.UserInfo;
import domain.UserType;
import jdbc.OfferJdbc;
import jdbc.OfferJdbcImpl;

public class OfferServiceImpl implements OfferService {

    OfferJdbc offerJdbc = new OfferJdbcImpl();

    @Override
    public List<Offer> getOfferRequests(UserInfo userInfo) {
        List<Offer> offerList;
        if(userInfo.getUserType().equals(UserType.DRIVER)) {
            offerList = getDriverOfferRequests();
        } else if(userInfo.getUserType().equals(UserType.PASSENGER)) {
            offerList = getPassengerOfferRequests();
        } else {
            return null;
        }

        return offerList;
    }

    protected List<Offer> getDriverOfferRequests() {
        return offerJdbc.getDriverOffers();
    }

    protected List<Offer> getPassengerOfferRequests() {
        return offerJdbc.getPassengerOffers();
    }

}
