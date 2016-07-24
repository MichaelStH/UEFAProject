package fr.esgi.iam.uefa;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import fr.esgi.iam.uefa.activities.LoginActivity;
import fr.esgi.iam.uefa.activities.TeamHomeActivity;
import fr.esgi.iam.uefa.activities.TeamSelectionActivity;
import fr.esgi.iam.uefa.app.MyApplication;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG =  SplashActivity.class.getSimpleName();

    private ProgressBar mProgressBar;

    private static final long TIME_POST_DELAYED = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = (ProgressBar) findViewById(R.id.splash_progressBar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Launch activity");

                //Verify if the progressBar is showed
                if (mProgressBar.isInLayout()) {
                    //Hide the progressBar
                    mProgressBar.setVisibility(View.GONE);
                }
                /*
                if( MyApplication.TEAM_IS_CHOSEN && MyApplication.USER_TOKEN_IS_VALID ){
                    Log.e(TAG, "teamAlreadyChosen launch home");

                    Intent intent = new Intent(SplashActivity.this, TeamHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else{
                    Log.e(TAG, "not teamAlreadyChosen launch selection team");

                    //Verify if the progressBar is showed
                    if (null != mProgressBar) {
                        //Hide the progressBar
                        mProgressBar.setVisibility(View.GONE);
                    }

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                */

                //Temp
                Intent intent = new Intent(SplashActivity.this, TeamSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        }, TIME_POST_DELAYED);
    }
}
