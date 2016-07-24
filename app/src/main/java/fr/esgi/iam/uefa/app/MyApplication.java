package fr.esgi.iam.uefa.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import fr.esgi.iam.uefa.rest.UefaRestClient;
import fr.esgi.iam.uefa.singleton.MySingleton;

/**
 * Created by MichaelWayne on 25/02/2016.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();

    public static MyApplication instance;
    private static Context context;

    private static UefaRestClient uefaRestClient;

    public static final String USER_SHARED_PREFS_TAG = "user_shared_prefs";
    public static final String USER_TOKEN_ARG = "user_token_arg";
    public static boolean USER_TOKEN_IS_VALID = false;
    public static final String USER_UID_ARG = "user_uid_arg";

    public static final String TEAM_SHARED_PREFS_TAG = "team_shared_prefs";

    public static final String TEAM_IS_CHOSEN_ARG = "team_is_choose_arg";
    public static boolean TEAM_IS_CHOSEN = false;

    public static final String TEAM_NATION_ARG = "team_nation_arg";

    public static final String TEAM_NATION_NAME_ARG = "team_nation_name_arg";
    public static final String TEAM_NATION_FLAG_ARG = "team_nation_flag_arg";

    public MyApplication(){
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        uefaRestClient = new UefaRestClient();
        USER_TOKEN_IS_VALID = isUserTokenValid();
        TEAM_IS_CHOSEN = teamAlreadyChosen();

        Log.i( TAG, "MyApplication - OnCreate()" );

    }

    public static Context getAppContext( )
    {
        return MyApplication.context;
    }

    /**
     * Return the RestClient object in order to use Retrofit library
     * @return uefaRestClient
     */
    public static UefaRestClient getUefaRestClient(){
        return uefaRestClient;
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

    public boolean isUserTokenValid(){

        boolean ok = false;
        String isValid = "";

        Log.e(TAG, "isUserTokenValid() - Read in SharedPrefs");

        SharedPreferences sharedPref = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        isValid = sharedPref.getString( MyApplication.USER_TOKEN_ARG, null );

        Log.e(TAG, "test valid : " + isValid );
        if ( null != isValid )
            ok = true;

        return ok;
    }

}
