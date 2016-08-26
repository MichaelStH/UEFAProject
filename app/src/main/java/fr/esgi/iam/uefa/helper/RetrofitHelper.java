package fr.esgi.iam.uefa.helper;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.activities.TeamBetActivity;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.fragments.BetsFragment;
import fr.esgi.iam.uefa.fragments.ProfileFragment;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Team;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 25/07/2016.
 */
public class RetrofitHelper {

    private static final String TAG = RetrofitHelper.class.getSimpleName();

    private static List<Match> matchesList = null;
    private static List<Team> teamsList = null;

    private static boolean isLoadData = false;

    //Cannot instantiate this class
    private RetrofitHelper(){}


    public static void getMatches(final BetsFragment context){

        MyApplication.getUefaRestClient().getApiService().getMatches(new Callback<List<Match>>() {
            @Override
            public void success(List<Match> matches, Response response) {
                if ( ! ( 200 == response.getStatus() ) ){
                    Log.e( TAG, "Another code occurred : " + response.getStatus());
                }else {
                    matchesList = new ArrayList<>();
                    matchesList = matches;

                    if (isLoadData == true)
                        context.onDataLoaded( matchesList,  teamsList );
                    else
                        isLoadData = true;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "RetrofitError : " + error.getMessage());
            }
        });

    }


    public static void getTeams(final BetsFragment context ){

        MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {
            @Override
            public void success(List<Team> teams, Response response) {
                if ( ! ( 200 == response.getStatus() ) ){
                    Log.e( TAG, "Another code occurred : " + response.getStatus());
                }else {
                    teamsList = new ArrayList<>();
                    teamsList = teams;

                    if (isLoadData == true)
                        context.onDataLoaded( matchesList, teamsList);
                    else
                        isLoadData = true;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "RetrofitError : " + error.getMessage());
            }
        });
    }

}
