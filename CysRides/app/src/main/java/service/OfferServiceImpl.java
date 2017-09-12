package service;

import java.util.ArrayList;
import java.util.List;

import domain.Offer;
import domain.UserInfo;
import domain.UserType;

public class OfferServiceImpl implements OfferService {

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
        List<Offer> dummyList = new ArrayList<>();
        for(int i=0 ; i<25 ; i++) {
            Offer offer = new Offer(UserType.PASSENGER, 53180.08, null, "Des Moines", "Take me to Des Moines Yo");
            dummyList.add(offer);
        }
        return dummyList;
    }

    protected List<Offer> getPassengerOfferRequests() {
        List<Offer> dummyList = new ArrayList<>();
        for(int i=0 ; i<5 ; i++) {
            Offer offer = new Offer(UserType.DRIVER, 53180.08, null, "Des Moines", "I'm going to Des Moines Yo");
            dummyList.add(offer);
        }
        return dummyList;
    }

}
