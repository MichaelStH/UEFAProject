package fr.esgi.iam.uefa.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class Utils {

    /** This class can't be instantiated. */
    private Utils(){}

    public static ActionBar getToolbar(AppCompatActivity context){
        ActionBar toolbar = context.getSupportActionBar();
        return toolbar;
    }

    public static void LaunchActivity (Context context, Class<? extends AppCompatActivity> aimedClass){
        Intent intent = new Intent(context, aimedClass);
        context.startActivity(intent);
    }

    public static void LaunchActivity (Context context, Class<? extends AppCompatActivity> aimedClass, Object jsonObject){
        Intent intent = new Intent(context, aimedClass);
        intent.putExtra( MyApplication.TEAM_NATION_ARG, (Team) jsonObject );
        context.startActivity(intent);
    }

    public static  void showActionInToast(Context context, String textToShow){
        Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
    }

    public static void dismissLoader(ProgressBar progressBar){
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            progressBar = null;
        }
    }

    public static boolean checkEditTextTextLength(String charSequence, int minLength, int maxLength){

        if ( minLength < charSequence.length() && charSequence.length() < maxLength)
            return true;
        else
            return false;

    }
}