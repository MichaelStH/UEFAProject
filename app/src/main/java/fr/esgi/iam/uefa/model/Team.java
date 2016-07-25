package fr.esgi.iam.uefa.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
public class Team implements Parcelable {

    public int id;
    public String name;
    public String points;
    public String played;
    public String won;
    public String drawn;
    public String lost;
    @SerializedName("BP")
    public String pointsFor;
    @SerializedName("BC")
    public String pointsAgainst;
    @SerializedName("diff")
    public String scale;
    public String coachName;
    public String history;
    public String flag;


    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(android.os.Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    protected Team(android.os.Parcel in) {
        id = in.readInt();
        name = in.readString();
        points = in.readString();
        played = in.readString();
        won = in.readString();
        drawn = in.readString();
        lost = in.readString();
        pointsFor = in.readString();
        pointsAgainst = in.readString();
        scale = in.readString();
        coachName = in.readString();
        history = in.readString();
        flag = in.readString();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(points);
        dest.writeString(played);
        dest.writeString(won);
        dest.writeString(drawn);
        dest.writeString(lost);
        dest.writeString(pointsFor);
        dest.writeString(pointsAgainst);
        dest.writeString(scale);
        dest.writeString(coachName);
        dest.writeString(history);
        dest.writeString(flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    ///////////////////////////////////////////////////////////////////////
    ////////////////////  GETTER & SETTER  //////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
