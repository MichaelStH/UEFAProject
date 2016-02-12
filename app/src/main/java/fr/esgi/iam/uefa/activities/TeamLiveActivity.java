package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.esgi.iam.uefa.R;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamLiveActivity extends AppCompatActivity {

    private static final String TAG = TeamLiveActivity.class.getSimpleName();

    private Context mContext = null;

    private View rootView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_live);
    }
}
