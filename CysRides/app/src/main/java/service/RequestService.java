package service;

import android.content.Context;

import domain.Offer;
import domain.Request;

public interface RequestService {
    void createRequest(Context context, Request request);
}
