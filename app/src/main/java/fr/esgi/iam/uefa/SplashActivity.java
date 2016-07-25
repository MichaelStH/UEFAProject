package fr.esgi.iam.uefa;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import fr.esgi.iam.uefa.model.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG =  SplashActivity.class.getSimpleName();
    private Context mContext;

    private ProgressBar mProgressBar;

    private static final long TIME_POST_DELAYED = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;

        mProgressBar = (ProgressBar) findViewById(R.id.splash_progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                isUserConnected();

/*
                Log.i(TAG, "Launch activity");

                //Verify if the progressBar is showed
                if (mProgressBar.isInLayout()) {
                    //Hide the progressBar
                    mProgressBar.setVisibility(View.GONE);
                }

                if( MyApplication.TEAM_IS_CHOSEN ){
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


                //Temp
                Intent intent = new Intent(SplashActivity.this, TeamSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
*/
            }
        }, TIME_POST_DELAYED);
    }

    private void isUserConnected(){

        //Get token save in shared prefs
        SharedPreferences sharedPref = getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        String userToken = sharedPref.getString( MyApplication.USER_TOKEN_ARG, "" );

        //Debug check
        Log.e(TAG, "Token from shared prefs : " + userToken);

        if ( userToken.equals("") ){
            Log.e( TAG, "Token is null" );

            Log.i(TAG, "Launch activity");

            //Verify if the progressBar is showed
            if (mProgressBar.isInLayout()) {
                //Hide the progressBar
                mProgressBar.setVisibility(View.GONE);
            }

            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }else{
            //Make REST call to verify if the token is still valid
            MyApplication.getUefaRestClient().getApiService().isUserConnected(userToken, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else {

                        if (null != userResponse.getError()) {
                            Log.e(TAG, "Error : " + userResponse.getError());
                            Toast.makeText(mContext, userResponse.getError(), Toast.LENGTH_SHORT).show();
                        }

                        //Verify if the progressBar is showed
                        if (null != mProgressBar) {
                            //Hide the progressBar
                            mProgressBar.setVisibility(View.GONE);
                        }

                        //Launch home activity
                        Intent intent = new Intent(SplashActivity.this, TeamHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, "RetrofitError : " + error.getMessage());
                }
            });
        }
    }
}
