package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Offer;
import domain.Request;
import domain.UserInfo;

public interface RequestService {
    void createRequest(Context context, Request request);
    ArrayList<Request> findOffersByEmail(ArrayList<Request> requests, UserInfo userInfo);
}
