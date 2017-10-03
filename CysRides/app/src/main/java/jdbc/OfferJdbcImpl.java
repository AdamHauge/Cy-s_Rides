package jdbc;

import java.util.ArrayList;
import java.util.List;

import domain.Offer;
import domain.UserType;

public class OfferJdbcImpl implements OfferJdbc {

    @Override
    public List<Offer> getDriverOffers() {
        List<Offer> dummyList = new ArrayList<>();
        for(int i=0 ; i<25 ; i++) {
            Offer offer = new Offer(UserType.PASSENGER, 53180.08, null, "Des Moines", "Take me to Des Moines Yo", null);
            dummyList.add(offer);
        }
        return dummyList;
    }

    @Override
    public List<Offer> getPassengerOffers() {
//        String sqlQuery = "select * from PASSENGER_REQUESTS_TABLE";
        List<Offer> dummyList = new ArrayList<>();
        for(int i=0 ; i<5 ; i++) {
            Offer offer = new Offer(UserType.DRIVER, 53180.08, null, "Des Moines", "I'm going to Des Moines Yo", null);
            dummyList.add(offer);
        }
        return dummyList;
    }

}
