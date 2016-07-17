package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.SplashActivity;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.ImageManagerUtils;

/**
 * Created by MichaelWayne on 01/06/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context mContext;

    private ImageView loginBackgroundImage;
    private EditText loginEditText, passwordEditText;
    private Button connectionButton;
    private ProgressBar mProgressBar;

    private static final long TIME_POST_DELAYED = 3500;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        initViews();

        //Blur the background
        ImageManagerUtils.setBlurredImage( mContext, loginBackgroundImage, 25 );

    }

    private void initViews(){
        loginBackgroundImage = (ImageView) findViewById(R.id.login_background_imageView);
        loginEditText = (EditText) findViewById(R.id.login_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);
        connectionButton = (Button) findViewById(R.id.login_connection_button);

        if (connectionButton != null)
            connectionButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.login_connection_button){
            DeviceManagerUtils.hideKeyboard(this, mContext);
            submitForm();
        }
    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!validateEmail()) {
            //Verify if the progressBar is showed
            if (mProgressBar.isInLayout()) {
                //Hide the progressBar
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            return;
        }

        if (!validatePassword()) {
            //Verify if the progressBar is showed
            if (mProgressBar.isInLayout()) {
                //Hide the progressBar
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Launch activity");

                //Verify if the progressBar is showed
                if (mProgressBar.isInLayout()) {
                    //Hide the progressBar
                    mProgressBar.setVisibility(View.INVISIBLE);
                }

                if( MyApplication.TEAM_IS_CHOSEN ){
                    Log.e(TAG, "teamAlreadyChosen launch home");

                    Intent intent = new Intent(LoginActivity.this, TeamHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else{
                    Log.e(TAG, "not teamAlreadyChosen launch selection team");

                    Intent intent = new Intent(LoginActivity.this, TeamSelectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        }, TIME_POST_DELAYED);


        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateEmail() {
        String email = loginEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            loginEditText.setError(getString(R.string.err_msg_email));
            requestFocus(loginEditText);
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordEditText.setError(getString(R.string.err_msg_password));
            requestFocus(passwordEditText);
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
