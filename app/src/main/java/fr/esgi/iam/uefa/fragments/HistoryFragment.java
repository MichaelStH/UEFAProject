package fr.esgi.iam.uefa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.RetrofitError;

/**
 * Created by MichaelWayne on 23/08/2016.
 */
public class HistoryFragment extends Fragment {

    private static final String TAG = HistoryFragment.class.getSimpleName();
    private Context mContext;

    private TextView homeTextViewContent;

    private Team bundle_team;


    public static Fragment newInstance( Team team ){
        HistoryFragment fragment = new HistoryFragment();

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
        return inflater.inflate(R.layout.fragment_history, container, false);
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
            MyApplication.getUefaRestClient().getApiService().getTeam(bundle_team.getId(), new retrofit.Callback<List<Team>>() {

                @Override
                public void success(List<Team> teams, retrofit.client.Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{
                        homeTextViewContent.setText(Html.fromHtml( teams.get(0).getHistory() ).toString() );
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    private void initViews(View view){
        homeTextViewContent = (TextView) view.findViewById(R.id.team_history_content);
        Log.i(TAG, "Views successfully initialized");
    }
}
