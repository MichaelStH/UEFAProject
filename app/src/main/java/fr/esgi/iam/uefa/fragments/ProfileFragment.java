package fr.esgi.iam.uefa.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.activities.TeamBetActivity;
import fr.esgi.iam.uefa.activities.TeamBetHistoryActivity;
import fr.esgi.iam.uefa.activities.TeamHistoryActivity;
import fr.esgi.iam.uefa.activities.TeamLiveActivity;
import fr.esgi.iam.uefa.activities.TeamPlayersActivity;
import fr.esgi.iam.uefa.activities.TeamRankingActivity;
import fr.esgi.iam.uefa.activities.TeamSelectionActivity;
import fr.esgi.iam.uefa.adapter.bet.BetHistoryListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Bet;
import fr.esgi.iam.uefa.model.BetArrayResponse;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.model.UserResponse;
import fr.esgi.iam.uefa.rest.UefaRestClient;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.DividerItemDecoration;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 21/08/2016.
 */
public class ProfileFragment extends Fragment  implements View.OnClickListener{

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private Context mContext;

    private Button changePasswordButton, changeTeamButton;
    private TextView teamNameTextView, userLoginTextView;
    private ImageView teamFlagImageView;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;

    private Team bundle_team;

    private BetHistoryListAdapter teamBetHistoryListAdapter;
    //Data
    private List<Bet> betList = new ArrayList<>();

    private String sharedPrefUserUID;


    public static Fragment newInstance( Team team ){
        ProfileFragment fragment = new ProfileFragment();

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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setListeners();

        try{

            loadUserInfos(view, savedInstanceState);
            loadTeamInfos();
            //loadUsersBetHistory(view, savedInstanceState);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

    }

    private void initViews(View view){

        userLoginTextView = (TextView) view.findViewById(R.id.team_profile_user_login_textView);

        teamNameTextView = (TextView) view.findViewById(R.id.team_profile_team_name_textView);
        teamFlagImageView = (ImageView) view.findViewById(R.id.team_profile_team_flag_imageView);

        changePasswordButton = (Button) view.findViewById(R.id.button_change_user_password);
        changeTeamButton = (Button) view.findViewById(R.id.button_change_team);

        mLoader = (ProgressBar) view.findViewById(R.id.team_profile_history_loader);
        contentRecyclerView = (RecyclerView) view.findViewById(R.id.team_profile_history_RecyclerView);
    }

    private void setListeners(){
        changeTeamButton.setOnClickListener( ProfileFragment.this );
        changePasswordButton.setOnClickListener( ProfileFragment.this );
    }


    @Override
    public void onClick(View view) {

        int id;

        id = view.getId();

        switch (id){

            case R.id.button_change_user_password:
                break;

            case R.id.button_change_team:
                Log.d(TAG, "Team Selection activity");
                Utils.LaunchActivity(mContext, TeamSelectionActivity.class);
                break;

            default:
                break;
        }
    }

    private void loadUserInfos(View view, Bundle savedInstanceState){
        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

        }
        else {
            SharedPreferences sharedPref = mContext.getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
            String userToken = sharedPref.getString( MyApplication.USER_TOKEN_ARG, "" );

            MyApplication.getUefaRestClient().getApiService().isUserConnected(userToken, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{
                        userLoginTextView.setText(userResponse.getUser().getLogin());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    private void loadTeamInfos(){

        //Fill the views
        teamNameTextView.setText( bundle_team.getName() );
        Picasso.with(mContext)
                .load(UefaRestClient.BASE_ENDPOINT + "/" + bundle_team.getFlag())
                .into(teamFlagImageView);

    }

    private void loadUsersBetHistory(View view, Bundle savedInstanceState) throws NullPointerException{
        getUserUIDFromSharedPrefs();

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

        }
        else {
            MyApplication.getUefaRestClient().getApiService().getUserBetsList(sharedPrefUserUID, new Callback<BetArrayResponse>() {
                @Override
                public void success(BetArrayResponse betResponse, Response response) {
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

    private void getUserUIDFromSharedPrefs(){
        SharedPreferences sharedPref = mContext.getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        sharedPrefUserUID = sharedPref.getString( MyApplication.USER_UID_ARG, "" );
    }
}
