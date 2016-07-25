package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.TeamSelectionGridAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamSelectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = TeamSelectionActivity.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    private View rootView;
    private GridView mGridView;
    private TeamSelectionGridAdapter mCountryAdapter;

    private ArrayList<Team> teamsList;
/*
    private String [] countriesName;
    private int [] countryFlags = {
            R.drawable.albania,
            R.drawable.germany,
            R.drawable.england,
            R.drawable.austria,
            R.drawable.belgium,
            R.drawable.croatia,
            R.drawable.spain,
            R.drawable.france,
            R.drawable.hungary,
            R.drawable.northern_ireland,
            R.drawable.island,
            R.drawable.italy,
            R.drawable.wales,
            R.drawable.poland,
            R.drawable.portugal,
            R.drawable.ireland,
            R.drawable.czech_republic,
            R.drawable.roumania,
            R.drawable.russia,
            R.drawable.slovakia,
            R.drawable.sweden,
            R.drawable.switzerland,
            R.drawable.turkey,
            R.drawable.ukraine
        };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        mContext = this;

        initViews();

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater().inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {

            MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {
                @Override
                public void success(List<Team> teams, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{
                        teamsList = new ArrayList<Team>();
                        teamsList = (ArrayList<Team>) teams;

                        mCountryAdapter = new TeamSelectionGridAdapter(mContext, teamsList);
                        mGridView.setAdapter(mCountryAdapter);

                        mGridView.setOnItemClickListener( TeamSelectionActivity.this );
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, "Retrofit error : " + error.getMessage());
                }
            });

        }
    }

    private void initViews(){
        rootView = getWindow().getDecorView();

        mGridView = (GridView) findViewById(R.id.team_selection_grid_view);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.team_selection_grid_view:

                try {

                    Team teamChosen = (Team) mCountryAdapter.getItem(position);

                    Intent intent = new Intent(mContext, TeamHomeActivity.class);

                    //Build the bundle to send to the other activity
                    intent.putExtra( MyApplication.TEAM_NATION_ARG, teamChosen );

                    //Save in Shared Prefs
                    saveInSharedPrefs( teamChosen );

                    //Launch Home Activity
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){
                    Log.e( TAG, e.getMessage() );
                }
                break;

            default:
                break;
        }
    }


    /**
     *
     * @param team
     */
    public void saveInSharedPrefs(Team team){

        SharedPreferences myPreference = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = myPreference.edit();

        editor.putBoolean( MyApplication.TEAM_IS_CHOSEN_ARG, true );

        Gson gson = new Gson();
        String json = gson.toJson(team);
        editor.putString( MyApplication.TEAM_NATION_ARG, json );
        editor.apply();

    }

}
