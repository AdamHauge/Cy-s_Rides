package domain;

/**
 * Created by Ryan on 12/3/2017.
 */

public class GOR {

    private Group group;
    private Offer offer;
    private Request request;

    public GOR(Group group, Offer offer, Request request){
        this.group = group;
        this.offer = offer;
        this.request = request;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
