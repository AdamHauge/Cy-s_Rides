package domain;

/**
 * Created by ZachKnoll on 10/30/2017.
 */

public class Ban {
    private String email;
    private String reason;

    public Ban() {

    }

    public Ban(String email, String reason) {
        this.email = email;
        this.reason = reason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
