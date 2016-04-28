package fr.esgi.iam.uefa.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
@Parcel
public class Team {

    public String idEquipe;
    @SerializedName("Name")
    public String name;
    @SerializedName("Points")
    public String points;
    @SerializedName("Played")
    public String played;
    @SerializedName("Won")
    public String won;
    @SerializedName("Drawn")
    public String drawn;
    @SerializedName("Lost")
    public String lost;
    @SerializedName("BP")
    public String pointsFor;
    @SerializedName("BC")
    public String pointsAgainst;
    @SerializedName("Diff")
    public String scale;
    @SerializedName("Selectionneur")
    public String selectionneur;
    @SerializedName("Flag")
    public String flag;


    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getDrawn() {
        return drawn;
    }

    public void setDrawn(String drawn) {
        this.drawn = drawn;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(String pointsFor) {
        this.pointsFor = pointsFor;
    }

    public String getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(String pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSelectionneur() {
        return selectionneur;
    }

    public void setSelectionneur(String selectionneur) {
        this.selectionneur = selectionneur;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
