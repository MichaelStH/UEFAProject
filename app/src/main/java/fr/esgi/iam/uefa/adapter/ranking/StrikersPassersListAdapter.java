package fr.esgi.iam.uefa.adapter.ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Matches;
import fr.esgi.iam.uefa.model.Player;

/**
 * Created by MichaelWayne on 01/05/2016.
 */
public class StrikersPassersListAdapter extends RecyclerView.Adapter<StrikersPassersListAdapter.ViewHolder>{

    private static final String TAG = JourneyListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Player> playerList;

    public StrikersPassersListAdapter(Context context, List<Player> playerList){

        this.context = context;
        this.playerList = playerList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView playerName, playerButs;

        public ViewHolder(View view) {
            super(view);

            playerName = (TextView) view.findViewById(R.id.item_team_ranking_str_pass_player_name);
            playerButs = (TextView) view.findViewById(R.id.item_team_ranking_str_pass_player_buts);
        }
    }


    @Override
    public int getItemCount() {
        return playerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPlayerRow = inflater.inflate(R.layout.team_ranking_str_pass_item_row, parent, false );

        return new ViewHolder(itemPlayerRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Player itemPlayer = playerList.get( position );

        holder.playerName.setText( itemPlayer.getName() );
        holder.playerButs.setText( itemPlayer.getButs() );
    }
}
