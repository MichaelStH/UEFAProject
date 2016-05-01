package fr.esgi.iam.uefa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import fr.esgi.iam.uefa.activities.TeamHomeActivity;
import fr.esgi.iam.uefa.activities.TeamSelectionActivity;
import fr.esgi.iam.uefa.app.MyApplication;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG =  SplashActivity.class.getSimpleName();

    private View rootView;
    private ProgressBar mProgressBar;

    private static final long TIME_POST_DELAYED = 3500;

    private boolean isTeamAlreadyChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rootView = getWindow().getDecorView();

        if (rootView != null){
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.splash_progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Launch activity");

                //Verify if the progressBar is showed
                if ( mProgressBar.isInLayout() ){
                    //Hide the progressBar
                    mProgressBar.setVisibility( View.GONE );
                }

                isTeamAlreadyChosen = teamAlreadyChosen();

                if( isTeamAlreadyChosen ){
                    Log.e(TAG, "teamAlreadyChosen launch home");

                    Intent intent = new Intent(SplashActivity.this, TeamHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else{
                    Log.e(TAG, "not teamAlreadyChosen launch selection team");

                    Intent intent = new Intent(SplashActivity.this, TeamSelectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIME_POST_DELAYED );
    }

    public boolean teamAlreadyChosen(){

        boolean ok = false;
        boolean isChosen = false;

        Log.e(TAG, "teamAlreadyChosen() - Read in SharedPrefs");

        SharedPreferences sharedPref = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        isChosen = sharedPref.getBoolean( MyApplication.TEAM_IS_CHOSEN_ARG, false );

        if ( isChosen == true )
            ok = true;

        return ok;
    }
}
