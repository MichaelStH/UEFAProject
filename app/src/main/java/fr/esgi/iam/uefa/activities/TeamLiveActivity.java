package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.live.LiveAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Action;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.DividerItemDecoration;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamLiveActivity extends AppCompatActivity {

    //TAG and context
    private static final String TAG = TeamLiveActivity.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    private View rootView;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private LiveAdapter teamLiveAdapter;

    private Team bundle_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_live);

        mContext = this;

        //Initialize  the view
        initViews();

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.dismissLoader(mLoader);
            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater().inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {

            MyApplication.getUefaRestClient().getApiService().getActions(new Callback<List<Action>>() {
                @Override
                public void success(List<Action> actions, Response response) {

                    //create the adapter
                    teamLiveAdapter = new LiveAdapter(mContext, actions);

                    //Set properties for the RecyclerView
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    contentRecyclerView.setLayoutManager(mLayoutManager);
                    contentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    contentRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

                    Utils.dismissLoader(mLoader);

                    if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                        contentRecyclerView.setVisibility(View.VISIBLE);
                    }

                    contentRecyclerView.setAdapter(teamLiveAdapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissLoader(mLoader);
                    Log.e(TAG, error.getMessage());
                }
            });

        }

    }

    private void initViews(){

        rootView = getWindow().getDecorView();

        mLoader = (ProgressBar) findViewById(R.id.team_live_loader);
        contentRecyclerView = (RecyclerView) findViewById(R.id.team_live_events_notifications_RecyclerView);
    }

}
