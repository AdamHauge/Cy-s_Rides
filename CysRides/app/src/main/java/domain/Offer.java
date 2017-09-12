package domain;

public class Offer {

    private UserType userType;
    private double cost;
    private UserInfo user;
    private String destination;
    private String description;

    public Offer(UserType userType, double cost, UserInfo user, String destination, String description) {
        this.userType = userType;
        this.cost = cost;
        this.user = user;
        this.destination = destination;
        this.description = description;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
