package fr.esgi.iam.uefa.fragments.ranking;

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
public class JourneyFragment extends Fragment {

    private static final String TAG = JourneyFragment.class.getSimpleName();

    private View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.team_ranking_journey_fragment, container, false);

        return rootView;
    }
}
