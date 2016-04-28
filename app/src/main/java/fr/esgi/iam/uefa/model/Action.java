package fr.esgi.iam.uefa.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
@Parcel
public class Action {

    public String idAction;
    public String idMatch;
    public String idButeur;
    public String temps;
    public String photo;
    @SerializedName("commentaire")
    public String comment;


    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////


    public String getIdAction() {
        return idAction;
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdButeur() {
        return idButeur;
    }

    public void setIdButeur(String idButeur) {
        this.idButeur = idButeur;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
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
