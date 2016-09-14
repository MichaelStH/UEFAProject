package fr.esgi.iam.uefa.adapter.live;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.players.PlayerListAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Action;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Player;
import fr.esgi.iam.uefa.model.Stadium;
import fr.esgi.iam.uefa.model.Team;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 01/05/2016.
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder>{

    private static final String TAG = PlayerListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Action> actionsList;

    public LiveAdapter(Context context, List<Action> actionsList){

        this.context = context;
        this.actionsList = actionsList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView matchTextView;
        public TextView stadiumTextView;
        public TextView timeTextView;
        public TextView playerTextView;
        public TextView commentTextView;

        public ViewHolder(View view) {
            super(view);

            matchTextView = (TextView) view.findViewById(R.id.team_live_event_match_TextView);
            stadiumTextView = (TextView) view.findViewById(R.id.team_live_event_stadium_textView);
            timeTextView = (TextView) view.findViewById(R.id.team_live_event_time_textView);
            playerTextView = (TextView) view.findViewById(R.id.team_live_event_player_textView);
            commentTextView = (TextView) view.findViewById(R.id.team_live_event_text_description_textView);
        }
    }


    @Override
    public int getItemCount() {
        return actionsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPlayerRow = inflater.inflate(R.layout.team_live_item_row, parent, false );

        return new ViewHolder(itemPlayerRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Action itemAction = actionsList.get( position );

        getMatchName( holder, itemAction.getIdMatch() );
        holder.timeTextView.setText( formatMatchTimeMinutes( itemAction.getTime() ) );
        getPlayerName( holder, Integer.valueOf( itemAction.getIdScorer() ) );
        holder.commentTextView.setText(Html.fromHtml( itemAction.getComment() ) );
    }

    private void getMatchName(final ViewHolder holder, int idMatch ){

        MyApplication.getUefaRestClient().getApiService().getMatches( idMatch, new Callback<List<Match>>() {
            @Override
            public void success(final List<Match> matches, Response response) {

                if ( matches != null ) {

                    //Variables
                    final int idTeam1 = matches.get(0).getIdTeam1();
                    final int idTeam2 = matches.get(0).getIdTeam2();

                    //REST Teams Call
                    MyApplication.getUefaRestClient().getApiService().getTeams(new Callback<List<Team>>() {
                        @Override
                        public void success(List<Team> teams, Response response) {

                            String team1 = "";
                            String team2 = "";

                            for (Team team : teams) {

                                if (idTeam1 == team.getId()){
                                    team1 = team.getName();
                                } else if ( idTeam2 == team.getId() ) {
                                    team2 = team.getName();
                                }
                            }

                            holder.matchTextView.setText( team1 + " vs " + team2 );
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getMessage());
                        }
                    });
                    //End REST Teams Call


                    final int idStadium = matches.get(0).getIdStadium();
                    //REST Stadium Call
                    MyApplication.getUefaRestClient().getApiService().getStadium(idStadium, new Callback<List<Stadium>>() {
                        @Override
                        public void success(List<Stadium> stadium, Response response) {

                            holder.stadiumTextView.setText( "( " + stadium.get(0).getName() + " )" );
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getMessage());
                        }
                    });
                    //End REST Stadium Call
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });

    }

    private void getPlayerName(final ViewHolder holder, int idPlayer ){

        MyApplication.getUefaRestClient().getApiService().getPlayers(idPlayer, new Callback<List<Player>>() {
            @Override
            public void success(List<Player> players, Response response) {
                if ( ! ( 200 == response.getStatus() ) ){
                    Log.e( TAG, "Another code occurred : " + response.getStatus());
                }else{
                    holder.playerTextView.setText( players.get(0).getFirstname() + " " + players.get(0).getSurname() );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private String formatMatchTimeMinutes( String time ){
        String minutes  = "";
        String [] split = time.split(":");

        if ( split != null && split.length == 3 ){
            minutes = String.format("%d'", Integer.parseInt( split[0] ) * 60 + Integer.parseInt( split[1] ) );
        }
        return minutes;

    }

}
