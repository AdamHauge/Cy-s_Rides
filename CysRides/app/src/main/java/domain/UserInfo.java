package domain;

import java.util.List;

public class UserInfo {

    private String username;
    private String password;
    private String email;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String venmoName;
    private float userRating;
    private List<Offer> offers;

    public UserInfo(String username, String password, String email, UserType userType, String firstName, String lastName,
                    String venmoName, float userRating, List<Offer> offers){
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.venmoName = venmoName;
        this.userRating = userRating;
        this.offers = offers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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
}
