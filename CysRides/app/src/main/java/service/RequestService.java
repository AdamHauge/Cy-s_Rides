package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Request;
import domain.UserInfo;

public interface RequestService {
    void createRequest(Context context, Request request);
    void deleteRequest(Context context, int id);
    ArrayList<Request> findRequestsByEmail(ArrayList<Request> requests, UserInfo userInfo);
    void giveRequestGroup(Context context, int requestID, int groupID);

    }
