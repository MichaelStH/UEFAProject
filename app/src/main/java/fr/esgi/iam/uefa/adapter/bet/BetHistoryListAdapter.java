package fr.esgi.iam.uefa.adapter.bet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Bet;

/**
 * Created by MichaelWayne on 25/07/2016.
 */
public class BetHistoryListAdapter  extends RecyclerView.Adapter<BetHistoryListAdapter.ViewHolder> {

    private static final String TAG = BetHistoryListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<String> teamsList;
    private List<Bet> betList;

    public BetHistoryListAdapter(Context context, List<Bet> betList, List<String> teamsList) {

        this.context = context;
        this.betList = betList;
        this.teamsList = teamsList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView, creditWageredTextView;

        public ViewHolder(View view) {
            super(view);

            idTextView = (TextView) view.findViewById(R.id.team_bet_history_id_textView_item);
            creditWageredTextView = (TextView) view.findViewById(R.id.team_bet_history_credits_wagered_textView_item);
        }
    }

    @Override
    public int getItemCount() {
        return betList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPlayerRow = inflater.inflate(R.layout.team_bet_history_item_row, parent, false);

        return new ViewHolder(itemPlayerRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Bet itemBet = betList.get(position);
        String itemTeam = teamsList.get(position);

        holder.idTextView.setText("Match : " + itemTeam);
        holder.creditWageredTextView.setText("Crédits misés : " + itemBet.getCreditsWagered());
    }


}