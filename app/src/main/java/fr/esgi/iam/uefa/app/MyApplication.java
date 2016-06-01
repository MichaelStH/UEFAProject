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

    public static final String TEAM_SHARED_PREFS_TAG = "team_shared_prefs";

    public static final String TEAM_IS_CHOSEN_ARG = "team_is_choose_arg";
    public static boolean TEAM_IS_CHOSEN = false;

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

}
