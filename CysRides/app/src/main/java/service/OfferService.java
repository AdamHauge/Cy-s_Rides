package service;

import java.util.List;

import domain.Offer;
import domain.UserInfo;

public interface OfferService {
    List<Offer> getOfferRequests(UserInfo userInfo);
}
