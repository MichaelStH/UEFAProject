package fr.esgi.iam.uefa;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Launch activity");

                //Verify if the progressBar is showed
                if ( mProgressBar.isInLayout() ){
                    //Hide the progressBar
                    mProgressBar.setVisibility( View.INVISIBLE );
                }

//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                //Temporary
                startActivity(new Intent(SplashActivity.this, TeamHomeActivity.class));
                finish();

            }
        }, TIME_POST_DELAYED );
    }
}
