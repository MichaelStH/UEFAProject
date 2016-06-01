package fr.esgi.iam.uefa.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.utils.Utils;
/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = TeamHomeActivity.class.getSimpleName();

    private Context mContext = null;

    private String bundle_team_name = "";
    private String bundle_team_flag = "";

    private Button teamHistoryButton, teamPlayersButton, teamRankingButton, teamLiveEvents;
    private View rootView = null;
    private TextView mTvHome;
    private ImageView mImgHome;

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

            String country_name = sharedPref.getString( MyApplication.TEAM_NATION_NAME_ARG, "" );
            String country_flag = sharedPref.getString( MyApplication.TEAM_NATION_FLAG_ARG, "" ) ;
            Bitmap bitmapFlag = null;

            if( !country_flag.equals("") )
                bitmapFlag = decodeToBase64(country_flag);

            if (bitmapFlag != null){
                mImgHome.setImageBitmap(bitmapFlag);
            }

            mTvHome.setText(country_name);

            return;
        }
        else
        {

            bundle_team_name = extras.getString( MyApplication.TEAM_NATION_NAME_ARG );
            bundle_team_flag = extras.getString( MyApplication.TEAM_NATION_FLAG_ARG );

            //Debug check
            Log.e(TAG, "Bundle not null");

            //Fill the views
            mTvHome.setText(bundle_team_name);

            Bitmap bitmapFlag = null;
            bitmapFlag = decodeToBase64(bundle_team_flag);
            mImgHome.setImageBitmap(bitmapFlag);

            mImgHome.setImageBitmap( bitmapFlag );
            Log.d(TAG, "Bundle Image loaded");
        }

    }

    private void initViews(){

        mTvHome = (TextView) findViewById(R.id.team_home_nation_textView);
        mImgHome = (ImageView) findViewById(R.id.team_home_imageView);

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


    /**
     * To display your image just convert it into Bitmap again using decodeToBase64 method
     * @param input
     * @return
     */
    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    @Override
    public void onClick(View view) {

        int id;

        id = view.getId();

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
