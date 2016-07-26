package fr.esgi.iam.uefa.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.SplashActivity;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.UserResponse;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.ImageManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import fr.esgi.iam.uefa.views.ForgotPasswordDialogFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 01/06/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ForgotPasswordDialogFragment.IDialogFrag {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context mContext;

    private ImageView loginBackgroundImage;
    private TextView loginForgotPasswordTextView;
    private EditText loginEditText, passwordEditText, forgotPasswordEditText;
    private Button registerButton, connectionButton;
    private ProgressBar mProgressBar;

    private static final long TIME_POST_DELAYED = 3500;

    private ForgotPasswordDialogFragment dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        initViews();

        //Blur the background
        ImageManagerUtils.setBlurredImage( mContext, loginBackgroundImage, 25 );

        dialog = new ForgotPasswordDialogFragment();
    }

    private void initViews(){
        loginBackgroundImage = (ImageView) findViewById(R.id.login_background_imageView);
        loginForgotPasswordTextView = (TextView) findViewById(R.id.login_forgot_password_textView);
        loginEditText = (EditText) findViewById(R.id.login_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);
        registerButton = (Button) findViewById(R.id.login_register_button);
        connectionButton = (Button) findViewById(R.id.login_connection_button);

        if ( loginForgotPasswordTextView != null )
            loginForgotPasswordTextView.setOnClickListener(this);

        if (registerButton != null)
            registerButton.setOnClickListener(this);

        if (connectionButton != null)
            connectionButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.login_forgot_password_textView:
                Toast.makeText(this, "Mot de passe oublié", Toast.LENGTH_SHORT).show();
                dialog.show(getSupportFragmentManager(), "forgotPasswordDialog");
                break;

            case R.id.login_register_button:
                Log.d(TAG, "Register activity");
                Utils.LaunchActivity(this, RegisterActivity.class);
                finish();
                break;

            case R.id.login_connection_button:
                DeviceManagerUtils.hideKeyboard(this, mContext);
                submitForm();
                break;

            default:
                break;
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

        connectUser();
    }

    public void connectUser(){
        String email = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        MyApplication.getUefaRestClient().getApiService().connectUser(email, password, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if ( ! ( 200 == response.getStatus() ) ){
                    Log.e( TAG, "Another code occurred : " + response.getStatus());
                }else{

                    if ( null != userResponse.getError() ) {
                        Log.e(TAG, "Error : " + userResponse.getError());
                        Utils.dismissLoader(mProgressBar);
                        Toast.makeText(mContext, userResponse.getError(), Toast.LENGTH_SHORT).show();
                    }else {

//                        Log.e(TAG, "test token got : " + userResponse.getUser().getToken());
                        saveUserUIDAndToken( userResponse.getUser().getUid(), userResponse.getUser().getToken() );
                        Utils.dismissLoader(mProgressBar);

                        if( MyApplication.TEAM_IS_CHOSEN ){
                            Log.e(TAG, "teamAlreadyChosen launch home");

                            Intent intent = new Intent(LoginActivity.this, TeamHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            /*
                            Utils.LaunchActivity(mContext, TeamHomeActivity.class);
                            finish();
                            */
                        } else if ( !MyApplication.TEAM_IS_CHOSEN && MyApplication.USER_TOKEN_IS_VALID ){

                            Log.e(TAG, "not teamAlreadyChosen launch selection team");

                            Intent intent = new Intent(LoginActivity.this, TeamSelectionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {

                            Utils.dismissLoader(mProgressBar);
                            Toast.makeText(mContext, "Veuillez vous enregistrer pour accéder au contenu de cette application", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public void saveUserUIDAndToken( String userUID, String userToken ){

        SharedPreferences sharedPref = getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString( MyApplication.USER_UID_ARG, userUID );
        editor.putString( MyApplication.USER_TOKEN_ARG, userToken );
        editor.apply();

    }


    private boolean validateEmail() {
        String email = loginEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            loginEditText.setError(getString(R.string.err_msg_email));
            requestFocus(loginEditText);
            return false;
        }

        if (!Utils.checkEditTextTextLength(email, 5, 40)){
            loginEditText.setError(getString(R.string.err_msg_email_length));
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

        if (!Utils.checkEditTextTextLength(passwordEditText.getText().toString().trim(), 6, 20)){
            passwordEditText.setError(getString(R.string.err_msg_password_length));
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

    private boolean validateForgotPasswordEmail( ) {
        String email = forgotPasswordEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            forgotPasswordEditText.setError(getString(R.string.err_msg_email));
            requestFocus(forgotPasswordEditText);
            return false;
        }

        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        forgotPasswordEditText = (EditText) dialog.getDialog().findViewById(R.id.forgot_password_mail_editText);

        if ( !validateForgotPasswordEmail( ) ){

        }
        else{
            if (forgotPasswordEditText != null) {
                Log.e(TAG, "Value is: " + forgotPasswordEditText.getText());
            } else {
                Log.e(TAG, "EditText not found!");
            }
        }

        Log.e(TAG, "Fin onPositiveClick");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

}


/**
 * old code
 *
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Log.i(TAG, "Launch activity");

        //Verify if the progressBar is showed
        if (mProgressBar.isInLayout()) {
            //Hide the progressBar
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        if( MyApplication.TEAM_IS_CHOSEN && MyApplication.USER_TOKEN_IS_VALID ){
            Log.e(TAG, "teamAlreadyChosen launch home");

            Intent intent = new Intent(LoginActivity.this, TeamHomeActivity.class);
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

            Toast.makeText(mContext, "Veullez vous enregistrer pour accéder au contenu de cette application", Toast.LENGTH_SHORT).show();

            *//*
            Intent intent = new Intent(LoginActivity.this, TeamSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            *//*
        }

    }
}, TIME_POST_DELAYED);
*/
