package fr.esgi.iam.uefa.model;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
@Parcel
public class Matches {

    public String id;
    public String date;
    public String time;
    public String idTeam1;
    public String idTeam2;
    public String goalTeam1;
    public String goalTeam2;
    public String score;
    public String idStade;

    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGoalTeam1() {
        return goalTeam1;
    }

    public void setGoalTeam1(String goalTeam1) {
        this.goalTeam1 = goalTeam1;
    }

    public String getGoalTeam2() {
        return goalTeam2;
    }

    public void setGoalTeam2(String goalTeam2) {
        this.goalTeam2 = goalTeam2;
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
