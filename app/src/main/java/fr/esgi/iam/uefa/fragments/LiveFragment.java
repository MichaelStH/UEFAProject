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
 * Created by MichaelWayne on 23/08/2016.
 */
public class LiveFragment extends Fragment {

    private static final String TAG = LiveFragment.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private LiveAdapter teamLiveAdapter;


    public static Fragment newInstance( ){
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

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

    private void initViews(View view){
        mLoader = (ProgressBar) view.findViewById(R.id.team_live_loader);
        contentRecyclerView = (RecyclerView) view.findViewById(R.id.team_live_events_notifications_RecyclerView);
    }
}
