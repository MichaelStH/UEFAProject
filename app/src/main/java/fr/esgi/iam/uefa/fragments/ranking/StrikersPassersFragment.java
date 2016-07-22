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
import java.util.List;
import java.util.Random;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.ranking.StrikersPassersListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Player;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 26/02/2016.
 */
public class StrikersPassersFragment extends Fragment {

    //TAG and context
    private static final String TAG = StrikersPassersFragment.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    private View rootView;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private StrikersPassersListAdapter strikersPassersListAdapter;

    //Data
    private List<Player> playerList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.team_ranking_str_pass_fragment, container, false);

        //Initialize  the view
        initViews(rootView);


        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.dismissLoader(mLoader);
            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) rootView);

        }
        else {

            MyApplication.getUefaRestClient().getApiService().getPlayers(new Callback<List<Player>>() {
                @Override
                public void success(List<Player> players, Response response) {

                    List<Player> tempList = new ArrayList<Player>();

                    //keep the Data
                    playerList = players;

                    for (Player player : playerList)
                    {

                        String randomName = playerList.get( new Random( ).nextInt( playerList.size( ) ) ).getSurname( );
//                        String randomButs = String.valueOf( new Random().nextInt( ( 8 + 1 ) ) );
                        String randomButs = player.getGoals();

                        tempList.add( new Player(randomName, randomButs ) );
                    }

                    //create the adapter
                    strikersPassersListAdapter = new StrikersPassersListAdapter(mContext, tempList);

                    //Set properties for the RecyclerView
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    contentRecyclerView.setLayoutManager(mLayoutManager);
                    contentRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    Utils.dismissLoader(mLoader);

                    if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                        contentRecyclerView.setVisibility(View.VISIBLE);
                    }

                    contentRecyclerView.setAdapter(strikersPassersListAdapter);

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

        mLoader = (ProgressBar) rootView.findViewById(R.id.team_ranking_str_pass_loader);
        contentRecyclerView = (RecyclerView) rootView.findViewById(R.id.team_ranking_str_pass_RecyclerView);

    }
}
