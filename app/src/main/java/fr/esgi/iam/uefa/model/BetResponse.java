package fr.esgi.iam.uefa.model;

/**
 * Created by MichaelWayne on 24/07/2016.
 */
public class BetResponse {

    public int code;
    public Bet bets;
    public String error;

    public Bet getBets() {
        return bets;
    }

    public void setBets(Bet bets) {
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
