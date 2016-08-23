package fr.esgi.iam.uefa.fragments;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.players.PlayerListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Player;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.DividerItemDecoration;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 23/08/2016.
 */
public class PlayersFragment extends Fragment {

    private static final String TAG = PlayersFragment.class.getSimpleName();
    private Context mContext;

    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private PlayerListAdapter teamPlayerListAdapter;

    //Data
    private List<Player> playerList = new ArrayList<>();

    private Team bundle_team;

    public static Fragment newInstance( Team team ){
        PlayersFragment fragment = new PlayersFragment();

        Bundle args = new Bundle();
        args.putParcelable( MyApplication.TEAM_NATION_ARG, team );
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        Bundle args = this.getArguments();
        if (args != null)
            bundle_team = args.getParcelable( MyApplication.TEAM_NATION_ARG );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_players, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.dismissLoader(mLoader);
            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

        }
        else {

            MyApplication.getUefaRestClient().getApiService().getPlayers(new Callback<List<Player>>() {
                @Override
                public void success(List<Player> players, Response response) {

                    for ( Player player : players ) {
                        if ( player.getIdTeam( ).equals( String.valueOf( bundle_team.getId( ) ) ) ) {

                            Log.d( "test", "nom du joueur : " + player.getSurname() );

                            playerList.add(player);
                        }
                    }

                    //create the adapter
                    teamPlayerListAdapter = new PlayerListAdapter(mContext, playerList);

                    //Set properties for the RecyclerView
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    contentRecyclerView.setLayoutManager(mLayoutManager);
                    contentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    contentRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

                    Utils.dismissLoader(mLoader);

                    if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                        contentRecyclerView.setVisibility(View.VISIBLE);
                    }

                    contentRecyclerView.setAdapter(teamPlayerListAdapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissLoader(mLoader);
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    private void initViews(View view){

        mLoader = (ProgressBar) view.findViewById(R.id.team_players_loader);
        contentRecyclerView = (RecyclerView) view.findViewById(R.id.team_players_RecyclerView);
    }
}
