package fr.esgi.iam.uefa.model;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
@Parcel
public class Matches {

    public String idMatches;
    public String date;
    public String time;
    public String idTeam1;
    public String idTeam2;
    public String score;
    public String idStade;

    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////


    public String getIdMatches() {
        return idMatches;
    }

    public void setIdMatches(String idMatches) {
        this.idMatches = idMatches;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdTeam1() {
        return idTeam1;
    }

    public void setIdTeam1(String idTeam1) {
        this.idTeam1 = idTeam1;
    }

    public String getIdTeam2() {
        return idTeam2;
    }

    public void setIdTeam2(String idTeam2) {
        this.idTeam2 = idTeam2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIdStade() {
        return idStade;
    }

    public void setIdStade(String idStade) {
        this.idStade = idStade;
    }

}
