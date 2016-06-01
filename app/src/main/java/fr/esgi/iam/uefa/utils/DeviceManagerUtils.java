package fr.esgi.iam.uefa.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by michael on 09/03/2016.
 */
public class DeviceManagerUtils {

    private static final String TAG = DeviceManagerUtils.class.getSimpleName();

    /** This class can't be instantiated. */
    private DeviceManagerUtils(){}


    //for example, permission can be "android.permission.WRITE_EXTERNAL_STORAGE"
    public static boolean hasPermission(Context context, String permission)
    {
        String prefix = "android.permission.";

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(prefix + permission.toUpperCase())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Check the Internet connection
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static void hideKeyboard(Activity activity, Context context){
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.getMessage();
        }
    }
}
