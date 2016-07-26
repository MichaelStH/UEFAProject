package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.rest.UefaRestClient;
import fr.esgi.iam.uefa.utils.Utils;
/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = TeamHomeActivity.class.getSimpleName();
    private Context mContext = null;

    private Button teamHistoryButton, teamPlayersButton, teamRankingButton, teamLiveEventsButton, teamBetHistoryButton, teamBetButton, disconnectionButton, changeTeamButton;
    private TextView mTvHome;
    private ImageView mImgHome;

    private Team bundle_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_home);

        mContext = this;

        initViews();
        setListeners();

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            Log.e(TAG, "Bundle null - Read in SharedPrefs");

            SharedPreferences sharedPref = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );

            Gson gson = new Gson();
            String json = sharedPref.getString( MyApplication.TEAM_NATION_ARG, "" );
            bundle_team = gson.fromJson(json, Team.class);

            if (bundle_team != null){
                mTvHome.setText(bundle_team.getName());
                Picasso.with(this)
                        .load(UefaRestClient.BASE_ENDPOINT + "/" + bundle_team.getFlag())
                        .into(mImgHome);
            }
            return;
        }
        else
        {

            bundle_team = extras.getParcelable( MyApplication.TEAM_NATION_ARG );

            //Debug check
            Log.e(TAG, "Bundle not null");

            //Fill the views
            mTvHome.setText( bundle_team.getName() );
            Picasso.with(this)
                    .load(UefaRestClient.BASE_ENDPOINT + "/" + bundle_team.getFlag())
                    .into(mImgHome);

            Log.d(TAG, "Bundle Image loaded");
        }

    }

    private void initViews(){

        mTvHome = (TextView) findViewById(R.id.team_home_nation_textView);
        mImgHome = (ImageView) findViewById(R.id.team_home_imageView);

        teamHistoryButton = (Button) findViewById(R.id.button_team_history);
        teamPlayersButton  = (Button) findViewById(R.id.button_team_players);
        teamRankingButton  = (Button) findViewById(R.id.button_team_ranking);
        teamLiveEventsButton = (Button) findViewById(R.id.button_team_live_events);
        teamBetHistoryButton = (Button) findViewById(R.id.button_team_bet_history);
        teamBetButton = (Button) findViewById(R.id.button_online_bets);

        disconnectionButton = (Button) findViewById(R.id.button_disconnect_user);
        changeTeamButton = (Button) findViewById(R.id.button_change_team);
        Log.i(TAG, "Views successfully initialized");
    }

    private void setListeners(){
        teamHistoryButton.setOnClickListener(this);
        teamPlayersButton.setOnClickListener(this);
        teamRankingButton.setOnClickListener(this);
        teamLiveEventsButton.setOnClickListener(this);
        teamBetHistoryButton.setOnClickListener(this);
        teamBetButton.setOnClickListener(this);
        disconnectionButton.setOnClickListener(this);
        changeTeamButton.setOnClickListener( this );
    }


    @Override
    public void onClick(View view) {

        int id;

        id = view.getId();

        switch (id){

            case R.id.button_disconnect_user:
                showAlertDialog();
                break;

            case R.id.button_change_team:
                Log.d(TAG, "Team Selection activity");
                Utils.LaunchActivity(this, TeamSelectionActivity.class);
                finish();
                break;

            case R.id.button_team_history :
                Log.d(TAG, "Launch history activity");
                Utils.LaunchActivity(this, TeamHistoryActivity.class, bundle_team);
                break;

            case R.id.button_team_players :
                Log.d(TAG, "Launch players activity");
                Utils.LaunchActivity(this, TeamPlayersActivity.class, bundle_team);
                break;

            case R.id.button_team_ranking :
                Log.d(TAG, "Launch ranking activity");
                Utils.LaunchActivity(this, TeamRankingActivity.class, bundle_team);
                break;

            case R.id.button_team_live_events :
                Log.d(TAG, "Launch live activity");
                Utils.LaunchActivity(this, TeamLiveActivity.class, bundle_team);
                break;

            case R.id.button_team_bet_history :
                Log.d(TAG, "Launch live activity");
                Utils.LaunchActivity(this, TeamBetHistoryActivity.class, bundle_team);
                break;

            case R.id.button_online_bets :
                Log.d(TAG, "Launch live activity");
                Utils.LaunchActivity(this, TeamBetActivity.class, bundle_team);
                break;

            default:
                break;
        }
    }

    /**
     * Build an alert dialog to ask the user if he wants to disconnect his account from the app
     */
    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        // set title
        alertDialogBuilder.setTitle("Déconnexion");

        // set dialog message
        alertDialogBuilder
                .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
                .setCancelable(false)
                .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        eraseSharedData();
                        Utils.LaunchActivity(mContext, LoginActivity.class);
                        TeamHomeActivity.this.finish();
                    }
                })
                .setNegativeButton("Non",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Erase data previously saved in shared preferences (token and uid)
     */
    private void eraseSharedData(){
        SharedPreferences sharedPref = getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString( MyApplication.USER_UID_ARG, "" );
        editor.putString( MyApplication.USER_TOKEN_ARG, "" );
        editor.apply();
    }
}

