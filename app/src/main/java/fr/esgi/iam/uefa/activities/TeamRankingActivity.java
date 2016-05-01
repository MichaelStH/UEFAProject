package fr.esgi.iam.uefa.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.fragments.ranking.JourneyFragment;
import fr.esgi.iam.uefa.fragments.ranking.RankingFragment;
import fr.esgi.iam.uefa.fragments.ranking.StrikersPassersFragment;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamRankingActivity extends AppCompatActivity {

    private static final String TAG = TeamRankingActivity.class.getSimpleName();

    private Context mContext = null;

    private View rootView = null;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_ranking);

        mContext = this;

        mViewPager = (ViewPager) findViewById(R.id.team_ranking_viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.ranking_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new JourneyFragment(), "Journée");
        adapter.addFragment(new RankingFragment(), "Classement");
        adapter.addFragment(new StrikersPassersFragment(), "Buteurs/Passeurs");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon (if there's icons)
            return mFragmentTitleList.get(position);
        }
    }
}
