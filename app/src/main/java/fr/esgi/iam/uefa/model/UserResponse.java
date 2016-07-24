package fr.esgi.iam.uefa.model;

/**
 * Created by MichaelWayne on 23/07/2016.
 */
public class UserResponse {

    public User user;
    public String code;
    public String error;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
