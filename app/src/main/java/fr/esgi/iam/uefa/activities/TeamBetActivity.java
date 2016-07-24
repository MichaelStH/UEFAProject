package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.BetResponse;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 23/07/2016.
 */
public class TeamBetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = TeamBetActivity.class.getSimpleName();
    private Context mContext;

    private View rootView;
    private Spinner teamName1Spinner, teamScore1Spinner, teamName2Spinner, teamScore2Spinner;
    private EditText moneyEditText;
    private Button betButton;

    private int scoreTeam1 = 0, scoreTeam2 = 0, creditsWagered = 0;
    private String userToken = "";

    private ArrayList<String> teamsNameList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_bet);

        mContext = this;

        initViews();

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater().inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {
            getTeamsNameList();
        }

    }

    private void initViews(){
        rootView = getWindow().getDecorView();

        teamName1Spinner = (Spinner) findViewById(R.id.team_bet_team_1_spinner);
        teamScore1Spinner = (Spinner) findViewById(R.id.team_bet_score_team_1_spinner);

        teamName2Spinner = (Spinner) findViewById(R.id.team_bet_team_2_spinner);
        teamScore2Spinner = (Spinner) findViewById(R.id.team_bet_score_team_2_spinner);

        moneyEditText = (EditText) findViewById(R.id.team_bet_money_editText);

        betButton = (Button) findViewById(R.id.team_bet_button);

        if ( betButton != null )
            betButton.setOnClickListener( TeamBetActivity.this );
    }

    private void getTeamsNameList(){

        MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {
            @Override
            public void success(List<Team> teams, Response response) {
                teamsNameList = new ArrayList<String>();

                for (Team team : teams)
                    teamsNameList.add(team.getName());

                setupSpinners(teamsNameList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private void setupSpinners(ArrayList<String> teamsNamesList){

        teamName1Spinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, teamsNamesList));
        teamName1Spinner .setOnItemSelectedListener( TeamBetActivity.this );

        teamName2Spinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, teamsNamesList));
        teamName2Spinner .setOnItemSelectedListener( TeamBetActivity.this );

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.team_bet_team_1_spinner:
                break;

            case R.id.team_bet_score_team_1_spinner:
                scoreTeam1 = Integer.valueOf( teamScore1Spinner.getSelectedItem().toString() );
                break;

            case R.id.team_bet_team_2_spinner:
                break;

            case R.id.team_bet_score_team_2_spinner:
                scoreTeam2 = Integer.valueOf( teamScore2Spinner.getSelectedItem().toString() );
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

        Log.e(TAG, "button click");

        SharedPreferences sharedPref = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        userToken = sharedPref.getString( MyApplication.USER_TOKEN_ARG, "" );

        if ( moneyEditText.getText().toString().isEmpty() ){
            moneyEditText.setError("Ce champs ne pas être vide");
        }else{
            creditsWagered = Integer.valueOf(moneyEditText.getText().toString());

            MyApplication.getUefaRestClient().getApiService().createBet(userToken, 7, creditsWagered, scoreTeam1, scoreTeam2, new Callback<BetResponse>() {
                @Override
                public void success(BetResponse betResponse, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{

                        Log.e(TAG, "Error : " + betResponse.getError() );
                        Log.e(TAG, "Response \n"
                                + betResponse.getBet().getUserUID() + "\n"
                                + betResponse.getBet().getCreditsWagered() + "\n"
                                + betResponse.getBet().getScoreMatch1() + "\n"
                                + betResponse.getBet().getScoreMatch2() + "\n");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }

    }
}
