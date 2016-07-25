package fr.esgi.iam.uefa.fragments.ranking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.ranking.JourneyListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 26/02/2016.
 */
public class JourneyFragment extends Fragment {

    //TAG and context
    private static final String TAG = JourneyFragment.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    private View rootView = null;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private JourneyListAdapter journeyListAdapter;

    private List<HashMap<Integer,String>> teamMapList = new ArrayList<>();
    private HashMap<Integer, String> teamMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.team_ranking_journey_fragment, container, false);

        //Initialize  the view
        initViews(rootView);


        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.dismissLoader(mLoader);
            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {

            MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {

                @Override
                public void success(List<Team> teams, Response response) {
                    for (Team team : teams){
                        teamMap.put( Integer.valueOf( team.getId( ) ), team.getName() );
                        teamMapList.add( teamMap );
                    }


                    MyApplication.getUefaRestClient().getApiService().getMatches(new Callback<List<Match>>() {
                        @Override
                        public void success(List<Match> matches, Response response) {

                            //create the adapter
                            journeyListAdapter = new JourneyListAdapter(mContext, matches, teamMapList);

                            //Set properties for the RecyclerView
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                            contentRecyclerView.setLayoutManager(mLayoutManager);
                            contentRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            Utils.dismissLoader(mLoader);

                            if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                                contentRecyclerView.setVisibility(View.VISIBLE);
                            }

                            contentRecyclerView.setAdapter(journeyListAdapter);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Utils.dismissLoader(mLoader);
                            Log.e(TAG, error.getMessage());
                        }
                    });

                }

                @Override
                public void failure(RetrofitError error) {

                    Utils.dismissLoader(mLoader);
                    Log.e(TAG, error.getMessage());
                }
            });

        }

        return rootView;
    }

    private void initViews(View rootView){

        mLoader = (ProgressBar) rootView.findViewById(R.id.team_ranking_journey_loader);
        contentRecyclerView = (RecyclerView) rootView.findViewById(R.id.team_ranking_journey_RecyclerView);

    }
}
