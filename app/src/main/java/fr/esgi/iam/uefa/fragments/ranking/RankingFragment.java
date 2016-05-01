package fr.esgi.iam.uefa.fragments.ranking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.esgi.iam.uefa.R;

/**
 * Created by MichaelWayne on 26/02/2016.
 */
public class RankingFragment extends Fragment {

    //TAG and context
    private static final String TAG = RankingFragment.class.getSimpleName();
    private Context mContext;

    private View rootView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.team_ranking_ranking_fragment, container, false);

        return rootView;
    }
}
