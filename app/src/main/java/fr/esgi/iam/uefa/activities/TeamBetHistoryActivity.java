package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.bet.BetHistoryListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Bet;
import fr.esgi.iam.uefa.model.BetResponse;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.DividerItemDecoration;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 25/07/2016.
 */
public class TeamBetHistoryActivity extends AppCompatActivity {

    private static final String TAG = TeamBetHistoryActivity.class.getSimpleName();
    private Context mContext;

    private View rootView;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;

    private BetHistoryListAdapter teamBetHistoryListAdapter;
    //Data
    private List<Bet> betList = new ArrayList<>();

    private String sharedPrefUserUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_bet_history);

        mContext = this;

        initViews();

        getUserUIDFromSharedPrefs();

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater().inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {
            MyApplication.getUefaRestClient().getApiService().getUserBetsList(sharedPrefUserUID, new Callback<BetResponse>() {
                @Override
                public void success(BetResponse betResponse, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{

                        for ( Bet bet : betResponse.getBets() ){
                            betList.add( bet );
                        }

                        //create the adapter
                        teamBetHistoryListAdapter = new BetHistoryListAdapter(mContext, betList);

                        //Set properties for the RecyclerView
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        contentRecyclerView.setLayoutManager(mLayoutManager);
                        contentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        contentRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

                        Utils.dismissLoader(mLoader);

                        if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                            contentRecyclerView.setVisibility(View.VISIBLE);
                        }

                        contentRecyclerView.setAdapter(teamBetHistoryListAdapter);
                    }
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
        mLoader = (ProgressBar) findViewById(R.id.team_bet_history_loader);
        contentRecyclerView = (RecyclerView) findViewById(R.id.team_bet_history_RecyclerView);
    }

    private void getUserUIDFromSharedPrefs(){
        SharedPreferences sharedPref = getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        sharedPrefUserUID = sharedPref.getString( MyApplication.USER_UID_ARG, "" );
    }
}
