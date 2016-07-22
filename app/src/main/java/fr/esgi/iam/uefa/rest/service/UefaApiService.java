package fr.esgi.iam.uefa.rest.service;

import java.util.List;

import fr.esgi.iam.uefa.model.Action;
import fr.esgi.iam.uefa.model.Article;
import fr.esgi.iam.uefa.model.Matches;
import fr.esgi.iam.uefa.model.Player;
import fr.esgi.iam.uefa.model.Team;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
public interface UefaApiService {

    @GET("/action/")
    void getActions(Callback<List<Action>> cb);

    @GET("/articles/")
    void getArticles(Callback<List<Article>> cb);

    @GET("/teams/")
    void getTeams(Callback<List<Team>> cb);

    @GET("/teams/")
    void getTeam(@Query(" id ") String id , Callback<Team> cb);

    @GET("/players/")
    void getPlayers(Callback<List<Player>> cb);

    @GET("/matches/")
    void getMatches(Callback<List<Matches>> cb);

}
