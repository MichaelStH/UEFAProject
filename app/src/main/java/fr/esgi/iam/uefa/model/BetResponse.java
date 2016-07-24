package fr.esgi.iam.uefa.model;

/**
 * Created by MichaelWayne on 24/07/2016.
 */
public class BetResponse {

    public Bet bet;
    public int code;
    public String error;

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
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
