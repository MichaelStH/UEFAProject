package fr.esgi.iam.uefa.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
@Parcel
public class Action {

    public int id;
    public int idMatch;
    public String idScorer;
    public String time;
    public String photo;
    public String comment;


    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdScorer() {
        return idScorer;
    }

    public void setIdScorer(String idScorer) {
        this.idScorer = idScorer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
