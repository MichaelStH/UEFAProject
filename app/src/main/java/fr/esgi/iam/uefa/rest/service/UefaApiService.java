package fr.esgi.iam.uefa.rest.service;

import java.util.List;

import fr.esgi.iam.uefa.model.Action;
import fr.esgi.iam.uefa.model.Article;
import fr.esgi.iam.uefa.model.Matches;
import fr.esgi.iam.uefa.model.Player;
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
    void disconnectUser(@Query( "disconnect" ) boolean bool, @Query("token") String userToken);

    @GET("/api/users/")
    void changeUserPassword(boolean isUpdateRequestMethod, String userToken, String newPassword, String password);

    @GET("/api/action/")
    void getActions(Callback<List<Action>> cb);

    @GET("/api/articles/")
    void getArticles(Callback<List<Article>> cb);

    @GET("/api/teams/")
    void getTeams(Callback<List<Team>> cb);

    @GET("/api/teams/")
    void getTeam(@Query("id") int id , Callback<List<Team>> cb);

    @GET("/api/players/")
    void getPlayers(Callback<List<Player>> cb);

    @GET("/api/matches/")
    void getMatches(Callback<List<Matches>> cb);

}
