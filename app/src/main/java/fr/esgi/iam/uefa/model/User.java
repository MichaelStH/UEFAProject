package fr.esgi.iam.uefa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MichaelWayne on 23/07/2016.
 */
public class User implements Parcelable {

    public String id;
    public String uid;
    public String login;
    public String password;
    public String token;
    public String credits;

    public User (String login, String password){
        this.login = login;
        this.password = password;
    }


    protected User(Parcel in) {
        id = in.readString();
        uid = in.readString();
        login = in.readString();
        password = in.readString();
        token = in.readString();
        credits = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(login);
        dest.writeString(password);
        dest.writeString(token);
        dest.writeString(credits);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}
