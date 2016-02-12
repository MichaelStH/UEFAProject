package fr.esgi.iam.uefa.utils;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.Toast;
/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class Utils {

    public static ActionBar getToolbar(AppCompatActivity context){
        ActionBar toolbar = context.getSupportActionBar();
        return toolbar;

    }

    public static void LaunchActivity (Context context, Class<? extends AppCompatActivity> aimedClass){
        Intent intent = new Intent(context, aimedClass);
        context.startActivity(intent);
    }

    public static  void showActionInToast(Context context, String textToShow){
        Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
    }
}