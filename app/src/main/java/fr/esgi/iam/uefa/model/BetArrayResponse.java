package fr.esgi.iam.uefa.model;

import java.util.ArrayList;

/**
 * Created by MichaelWayne on 24/07/2016.
 */
public class BetArrayResponse {

    public int code;
    public ArrayList<Bet> bets;
    public String error;

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
