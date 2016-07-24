package fr.esgi.iam.uefa.model;

/**
 * Created by MichaelWayne on 24/07/2016.
 */
public class Bet {

    public int id;
    public String userUID;
    public int creditsWagered;
    public int idMatch;
    public int scoreMatch1;
    public int scoreMatch2;


    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public int getCreditsWagered() {
        return creditsWagered;
    }

    public void setCreditsWagered(int creditsWagered) {
        this.creditsWagered = creditsWagered;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public int getScoreMatch1() {
        return scoreMatch1;
    }

    public void setScoreMatch1(int scoreMatch1) {
        this.scoreMatch1 = scoreMatch1;
    }

    public int getScoreMatch2() {
        return scoreMatch2;
    }

    public void setScoreMatch2(int scoreMatch2) {
        this.scoreMatch2 = scoreMatch2;
    }
}
