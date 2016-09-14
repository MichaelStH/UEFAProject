package fr.esgi.iam.uefa.fragments.ranking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Team;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 26/02/2016.
 */
public class RankingFragment extends Fragment {

    //TAG and context
    private static final String TAG = RankingFragment.class.getSimpleName();
    private Context mContext;

    private View rootView = null;
    private TextView teamTextView1, teamTextView2, teamTextView3, teamTextView4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.team_ranking_ranking_fragment, container, false);

        initViews(rootView);

        MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {
            @Override
            public void success(List<Team> teams, Response response) {

                teamTextView1.setText( teams.get(0).getName() );
                teamTextView2.setText( teams.get(1).getName() );
                teamTextView3.setText( teams.get(2).getName() );
                teamTextView4.setText( teams.get(3).getName() );

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e( TAG, error.getMessage() );
            }
        });

        return rootView;
    }

    private void initViews(View view){
        teamTextView1 = (TextView) view.findViewById(R.id.team_ranking_ranking_1_textView);
        teamTextView2 = (TextView) view.findViewById(R.id.team_ranking_ranking_2_textView);
        teamTextView3 = (TextView) view.findViewById(R.id.team_ranking_ranking_3_textView);
        teamTextView4 = (TextView) view.findViewById(R.id.team_ranking_ranking_4_textView);
    }
}
