package fr.esgi.iam.uefa.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.TeamSelectionGridAdapter;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamSelectionActivity extends AppCompatActivity {

    private static final String TAG = TeamSelectionActivity.class.getSimpleName();

    private Context mContext;

    private GridView mGridView;
    private TeamSelectionGridAdapter mContryAdapter;

    private String [] countriesName;
    private int [] countryFlags = {
            R.drawable.albania,
            R.drawable.germany,
            R.drawable.england,
            R.drawable.austria,
            R.drawable.belgium,
            R.drawable.croatia,
            R.drawable.spain,
            R.drawable.france,
            R.drawable.hungary,
            R.drawable.northern_ireland,
            R.drawable.island,
            R.drawable.italy,
            R.drawable.wales,
            R.drawable.poland,
            R.drawable.portugal,
            R.drawable.ireland,
            R.drawable.czech_republic,
            R.drawable.roumania,
            R.drawable.russia,
            R.drawable.slovakia,
            R.drawable.sweden,
            R.drawable.switzerland,
            R.drawable.turkey,
            R.drawable.ukraine
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        mContext = this;
        countriesName = getApplicationContext().getResources().getStringArray(R.array.countries);

        mGridView = (GridView) findViewById(R.id.team_selection_grid_view);

        mContryAdapter = new TeamSelectionGridAdapter(mContext, countriesName, countryFlags);
        mGridView.setAdapter(mContryAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, TeamHomeActivity.class);
                intent.putExtra("contry_name", (String) mContryAdapter.getItem(position));
                intent.putExtra("contry_flag", (Integer) mContryAdapter.getItem(position));
                //startActivity(intent);
            }
        });
    }
}
