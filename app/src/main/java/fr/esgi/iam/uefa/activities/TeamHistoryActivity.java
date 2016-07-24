package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamHistoryActivity extends AppCompatActivity {

    private static final String TAG = TeamHistoryActivity.class.getSimpleName();

    private Context mContext = null;

    private View rootView = null;
    private TextView homeTextViewContent;

    private Team bundle_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_history);

        mContext = this;

        initViews();

        Bundle extras = getIntent().getExtras();
        if ( null != extras ) {
            bundle_team = extras.getParcelable( MyApplication.TEAM_NATION_ARG );
        }

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater().inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {
            MyApplication.getUefaRestClient().getApiService().getTeam(Integer.valueOf(bundle_team.getId()), new Callback<List<Team>>() {

                @Override
                public void success(List<Team> teams, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{
                        homeTextViewContent.setText( teams.get(0).getHistory() );
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    private void initViews(){

        rootView = getWindow().getDecorView();
        homeTextViewContent = (TextView) findViewById(R.id.team_history_content);
    }
}
