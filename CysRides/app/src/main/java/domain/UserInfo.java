package domain;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    private String netID;
    private String password;
    private int confirmationCode;
    private String firstName;
    private String lastName;
    private String venmoName;
    private String profileDescription;
    private UserType userType;
    private float userRating;
    private List<Offer> offers;
    private List<Request> requests;

    public UserInfo(String netID, String password, int confirmationCode, String firstName, String lastName, String venmoName,
                    String profileDescription, UserType userType, float userRating, List<Offer> offers, List<Request> requests) {
        this.netID = netID;
        this.password = password;
        this.confirmationCode = confirmationCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.venmoName = venmoName;
        this.profileDescription = profileDescription;
        this.userType = userType;
        this.userRating = userRating;
        this.offers = offers;
        this.requests = requests;
    }

    //TODO fix dummy fields
    public UserInfo toUserInfo(String userInfoString) {
        String netID = "";
        String password = "";
        int confirmationCode = 0;
        String firstName = "";
        String lastName = "";
        String venmoName = "";
        String profileDescription = "";
        UserType userType = UserType.PASSENGER;
        float userRating = 0;
        List<Offer> offers = new ArrayList<Offer>();
        List<Request> requests = new ArrayList<Request>();

        UserInfo userInfo = new UserInfo(netID, password, confirmationCode, firstName, lastName, venmoName,
                profileDescription, userType, userRating, offers, requests);
        return userInfo;
    }

    public String ratingToString(float userRating) {
        return Float.toString(userRating);
    }

    public String confirmationCodeToString(int confirmationCode) {
        return Integer.toString(confirmationCode);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "netID='" + netID + '\'' +
                ", password='" + password + '\'' +
                ", confirmationCode='" + confirmationCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", venmoName='" + venmoName + '\'' +
                ", profileDescription='" + profileDescription + '\'' +
                ", userType=" + userType +
                ", userRating=" + userRating +
                ", offers=" + offers +
                ", requests=" + requests +
                '}';
    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(int confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVenmoName() {
        return venmoName;
    }

    public void setVenmoName(String venmoName) {
        this.venmoName = venmoName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}
