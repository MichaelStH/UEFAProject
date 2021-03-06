package fr.esgi.iam.uefa.rest.service;

import java.util.List;

import fr.esgi.iam.uefa.model.Action;
import fr.esgi.iam.uefa.model.Article;
import fr.esgi.iam.uefa.model.BetArrayResponse;
import fr.esgi.iam.uefa.model.BetResponse;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Player;
import fr.esgi.iam.uefa.model.Stadium;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.model.UserResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
public interface UefaApiService {

    @FormUrlEncoded
    @POST("/api/users/")
    void createNewUser(@Field("login") String loginUser, @Field("password") String loginPassword, Callback<UserResponse> callback);

    @GET("/api/users/")
    void connectUser(@Query("login") String login, @Query("password") String password, Callback <UserResponse> callback) ;

    @GET("/api/users/")
    void isUserConnected(@Query("token") String userToken, Callback <UserResponse> callback) ;

    @GET("/api/users/")
    void disconnectUser(@Query( "disconnect" ) boolean bool, @Query("token") String userToken);

    @GET("/api/users/")
    void changeUserPassword(@Query("UpdateRequestMethod") boolean isUpdateRequestMethod, @Query("token") String userToken, @Query("newPassword") String newPassword, @Query("password") String password, Callback<UserResponse> callback);

    @GET("/api/articles/")
    void getArticles(Callback<List<Article>> cb);

    @GET("/api/action/")
    void getActions(Callback<List<Action>> cb);

    @GET("/api/teams/")
    void getTeams(Callback<List<Team>> cb);

    @GET("/api/teams/")
    void getTeam(@Query("id") int id , Callback<List<Team>> cb);

    @GET("/api/players/")
    void getPlayers(Callback<List<Player>> cb);

    @GET("/api/players/")
    void getPlayers(@Query("id") int id ,Callback<List<Player>> cb);

    @GET("/api/matches/")
    void getMatches(Callback<List<Match>> cb);

    @GET("/api/matches/")
    void getMatches(@Query("id") int id , Callback<List<Match>> cb);

    @GET("/api/stadiums/")
    void getStadiums( Callback<List<Stadium>> cb);

    @GET("/api/stadiums/")
    void getStadium(@Query("id") int id , Callback<List<Stadium>> cb);

    @FormUrlEncoded
    @POST("/api/bets/")
    void createBet(@Field("token") String userToken, @Field("idMatch") int idMatch, @Field("creditsWagered") int creditsWagered, @Field("scoreTeam1") int scoreTeam1, @Field("scoreTeam2") int scoreTeam2, Callback<BetResponse> callback);

    @GET("/api/bets/")
    void getUserBetsList(@Query("uid") String userUID,  Callback<BetArrayResponse> cb);

}
