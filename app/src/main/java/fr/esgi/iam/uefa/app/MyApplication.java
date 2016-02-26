package fr.esgi.iam.uefa.app;

import android.app.Application;
import android.content.Context;

import fr.esgi.iam.uefa.singleton.MySingleton;

/**
 * Created by MichaelWayne on 25/02/2016.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();

    public static MyApplication instance;
    private static Context context;

    public static final String TEAM_SHARED_PREFS_TAG = "team_shared_prefs";

    public static final String TEAM_NATION_NAME_ARG = "team_nation_name_arg";
    public static final String TEAM_NATION_FLAG_ARG = "team_nation_flag_arg";

    public MyApplication(){
        instance = this;
    }

    public static Context getAppContext( )
    {
        return MyApplication.context;
    }
}
