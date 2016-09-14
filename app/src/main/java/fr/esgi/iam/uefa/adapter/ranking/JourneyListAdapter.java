package fr.esgi.iam.uefa.adapter.ranking;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Stadium;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 01/05/2016.
 */
public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.ViewHolder>{

    private static final String TAG = JourneyListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Match> matchesList;
    private List<HashMap<Integer, String>> teamMapList;

    public JourneyListAdapter(Context context, List<Match> matchesList, List<HashMap<Integer, String>> teamMapList){

        this.context = context;
        this.matchesList = matchesList;

        this.teamMapList = teamMapList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView item_team1, item_team2, item_stadium, item_time;


        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.item_team_ranking_journey_cardView);

            item_team1 = (TextView) view.findViewById(R.id.item_team_ranking_journey_team_1);
            item_team2 = (TextView) view.findViewById(R.id.item_team_ranking_journey_team_2);
            item_stadium = (TextView) view.findViewById(R.id.item_team_ranking_journey_stadium_textView);
            item_time = (TextView) view.findViewById(R.id.item_team_ranking_journey_time_textView);
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

        Match itemMatch = matchesList.get( position );

        holder.item_team1.setText( teamMapList.get( Integer.valueOf( itemMatch.getIdTeam1( ) ) -1 ).get( Integer.valueOf( itemMatch.getIdTeam1( ) ) ) );
        holder.item_team2.setText( teamMapList.get( Integer.valueOf( itemMatch.getIdTeam2( ) ) -1 ).get( Integer.valueOf( itemMatch.getIdTeam2( ) ) ) );

        getStadiumName( holder, itemMatch.getIdStadium() );
        holder.item_time.setText( itemMatch.getTime() );
    }

    private void getStadiumName(final ViewHolder holder, int idStadium ){
        MyApplication.getUefaRestClient().getApiService().getStadium(idStadium, new Callback<List<Stadium>>() {
            @Override
            public void success(List<Stadium> stadium, Response response) {
                holder.item_stadium.setText( stadium.get(0).getName() );
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

}
