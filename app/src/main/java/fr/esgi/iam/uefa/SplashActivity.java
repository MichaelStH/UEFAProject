package fr.esgi.iam.uefa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import fr.esgi.iam.uefa.activities.TeamHomeActivity;
import fr.esgi.iam.uefa.activities.TeamSelectionActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG =  SplashActivity.class.getSimpleName();

    private Context mContext;

    private View rootView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rootView = getWindow().getDecorView();

        mProgressBar = null;

        if (rootView != null){
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.splash_progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally {

                    Log.i(TAG, "Launch activity");

                    Intent intent = new Intent(SplashActivity.this, TeamSelectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();

    }

    private void methodTest(){
        int i = 1+1;
    }
}
