package fr.esgi.iam.uefa.adapter.ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Matches;

/**
 * Created by MichaelWayne on 01/05/2016.
 */
public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.ViewHolder>{

    private static final String TAG = JourneyListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Matches> matchesList;
    private List<HashMap<Integer, String>> teamMapList;

    public JourneyListAdapter(Context context, List<Matches> matchesList, List<HashMap<Integer, String>> teamMapList){

        this.context = context;
        this.matchesList = matchesList;

        this.teamMapList = teamMapList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView team1, team2;

        public ViewHolder(View view) {
            super(view);

            team1 = (TextView) view.findViewById(R.id.item_team_ranking_journey_team_1);
            team2 = (TextView) view.findViewById(R.id.item_team_ranking_journey_team_2);
        }
    }


    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPlayerRow = inflater.inflate(R.layout.team_ranking_journey_item_row, parent, false );

        return new ViewHolder(itemPlayerRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Matches itemMatch = matchesList.get( position );

        holder.team1.setText( teamMapList.get( Integer.valueOf( itemMatch.getIdTeam1( ) ) -1 ).get( Integer.valueOf( itemMatch.getIdTeam1( ) ) ) );
        holder.team2.setText( teamMapList.get( Integer.valueOf( itemMatch.getIdTeam2( ) ) -1 ).get( Integer.valueOf( itemMatch.getIdTeam2( ) ) ) );
    }

}
