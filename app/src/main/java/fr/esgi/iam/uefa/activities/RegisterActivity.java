package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.User;
import fr.esgi.iam.uefa.model.UserResponse;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 23/07/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Context mContext;

    private EditText registerLoginEditText, registerNewPasswordEditText, registerPasswordEditText;
    private Button cancelButton, registerButton;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;

        initViews();
    }

    private void initViews(){
        registerLoginEditText = (EditText) findViewById(R.id.register_mail_editText);
        registerNewPasswordEditText = (EditText) findViewById(R.id.register_new_password_editText);
        registerPasswordEditText = (EditText) findViewById(R.id.register_password_editText);

        cancelButton = (Button) findViewById(R.id.register_cancel_button);
        registerButton = (Button) findViewById(R.id.register_register_button);

        if (registerButton != null)
            registerButton.setOnClickListener(this);

        if (cancelButton != null)
            cancelButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

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

        if (!validateNewPassword()) {
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

        if (!arePasswordsEqual()){
            //Verify if the progressBar is showed
            if (mProgressBar.isInLayout()) {
                //Hide the progressBar
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            Toast.makeText(this, "Password are not equal", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(
                registerLoginEditText.getText().toString().trim(),
                registerPasswordEditText.getText().toString().trim() );

        MyApplication.getUefaRestClient().getApiService().createNewUser(user.getLogin(), user.getPassword(), new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {

                if ( 200 == response.getStatus() ){

                    //Test
//                    Toast.makeText(mContext, "Everything is alright", Toast.LENGTH_LONG).show();

                    Log.e(TAG, "test token got : " + userResponse.getUser().getToken());
                    saveUserUIDAndToken( userResponse.getUser().getUid(), userResponse.getUser().getToken() );

                    Utils.LaunchActivity(mContext, TeamSelectionActivity.class);
                    finish();
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
        String email = registerLoginEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            registerLoginEditText.setError(getString(R.string.err_msg_email));
            requestFocus(registerLoginEditText);
            return false;
        }

        if (!Utils.checkEditTextTextLength(email, 5, 40)){
            registerLoginEditText.setError(getString(R.string.err_msg_email_length));
            return false;
        }

        return true;
    }

    private boolean validateNewPassword() {
        String password = registerNewPasswordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            registerNewPasswordEditText.setError(getString(R.string.err_msg_password));
            requestFocus(registerNewPasswordEditText);
            return false;
        }

        if (!Utils.checkEditTextTextLength(password, 6, 20)){
            registerNewPasswordEditText.setError(getString(R.string.err_msg_password_length));
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        String password = registerPasswordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            registerPasswordEditText.setError(getString(R.string.err_msg_password));
            requestFocus(registerPasswordEditText);
            return false;
        }

        if (!Utils.checkEditTextTextLength(password, 6, 20)){
            registerPasswordEditText.setError(getString(R.string.err_msg_password_length));
            return false;
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean arePasswordsEqual(){
        String newPassword = registerNewPasswordEditText.getText().toString().trim();
        String password = registerPasswordEditText.getText().toString().trim();
        if ( !newPassword.equals(password) )
            return false;

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.register_cancel_button:
                finish();
                break;

            case R.id.register_register_button:
                DeviceManagerUtils.hideKeyboard(this, mContext);
                submitForm();
                break;

            default:
                break;
        }
    }
}
