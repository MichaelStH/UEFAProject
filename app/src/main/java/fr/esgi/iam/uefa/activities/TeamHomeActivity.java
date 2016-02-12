package fr.esgi.iam.uefa.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.utils.Utils;
/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = TeamHomeActivity.class.getSimpleName();

    private Context mContext = null;

    private Button teamHistoryButton, teamPlayersButton, teamRankingButton, teamLiveEvents;
    private View rootView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_home);

        mContext = this;

        initViews();
        setListeners();
    }

    private void initViews(){
        teamHistoryButton = (Button) findViewById(R.id.button_team_history);
        teamPlayersButton  = (Button) findViewById(R.id.button_team_players);
        teamRankingButton  = (Button) findViewById(R.id.button_team_ranking);
        teamLiveEvents  = (Button) findViewById(R.id.button_team_live_events);

        Log.i(TAG, "Views successfully initialized");
    }

    private void setListeners(){
        teamHistoryButton.setOnClickListener(this);
        teamPlayersButton.setOnClickListener(this);
        teamRankingButton.setOnClickListener(this);
        teamLiveEvents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id;

        id = v.getId();

        switch (id){
            case R.id.button_team_history :
                Log.d(TAG, "Launch history activity");
                Utils.LaunchActivity(this, TeamHistoryActivity.class);
                break;

            case R.id.button_team_players :
                Log.d(TAG, "Launch players activity");
                Utils.LaunchActivity(this, TeamPlayersActivity.class);
                break;

            case R.id.button_team_ranking :
                Log.d(TAG, "Launch ranking activity");
                Utils.LaunchActivity(this, TeamRankingActivity.class);
                break;

            case R.id.button_team_live_events :
                Log.d(TAG, "Launch live activity");
                Utils.LaunchActivity(this, TeamLiveActivity.class);
                break;

            default:
                break;
        }
    }
}
