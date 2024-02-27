package xavr.todolist.security;

import java.util.Date;

public class TokenPayload {

    private Long userID;
    private String email;
    private Date timeOfCreation;

    public TokenPayload(Long userID, String email, Date timeOfCreation) {
        this.userID = userID;
        this.email = email;
        this.timeOfCreation = timeOfCreation;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
