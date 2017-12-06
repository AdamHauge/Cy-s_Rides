package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Request;
import domain.UserInfo;
import volley.RequestVolley;
import volley.RequestVolleyImpl;

public class RequestServiceImpl implements RequestService {

    private RequestVolley requestVolley = new RequestVolleyImpl();

    /**
     * Creates a request in the database
     * @param context
     * @param request
     */
    @Override
    public void createRequest(Context context, Request request) {
        String destName = String.format("%s %s", request.getDestination(), request.getDestCoordinates().toString());
        String startName = String.format("%s %s", request.getStart(), request.getStartCoordinates().toString());
        requestVolley.createRequest(context, request, destName, startName);
    }

    /**
     * Deletes a request in the database using a request id
     * @param context
     * @param id
     */
    @Override
    public void deleteRequest(Context context, int id) {
        requestVolley.deleteRequest(context, id);
    }

    //Finds the requests for a specific email

    /**
     * Finds the requests by an email from the database
     * @param requests
     * @param userInfo
     * @return an arraylist of requests from the database
     */
    @Override
    public ArrayList<Request> findRequestsByEmail(ArrayList<Request> requests, UserInfo userInfo) {
        ArrayList<Request> emailRequests = new ArrayList<>();
        for(int i=0 ; i<requests.size() ; i++) {
            if(requests.get(i).getEmail().equals(userInfo.getNetID())) {
                emailRequests.add(requests.get(i));
            }
        }
        return emailRequests;
    }

    /**
     * Gives specified request the given groupID
     * @param context
     * @param requestID
     * @param groupID
     */
    @Override
    public void giveRequestGroup(Context context, int requestID, int groupID){
        requestVolley.giveRequestGroup(context, requestID, groupID);
    }

    //protected List<Offer> getDriverOfferRequests() {
    //    return offerJdbc.getDriverOffers();
    //}

    //protected List<Offer> getPassengerOfferRequests() {
    //    return offerJdbc.getPassengerOffers();
    //}

}
