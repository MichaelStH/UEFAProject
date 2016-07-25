package fr.esgi.iam.uefa.adapter.bet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Bet;
import fr.esgi.iam.uefa.model.Player;

/**
 * Created by MichaelWayne on 25/07/2016.
 */
public class BetHistoryListAdapter  extends RecyclerView.Adapter<BetHistoryListAdapter.ViewHolder> {

    private static final String TAG = BetHistoryListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Bet> betList;

    public BetHistoryListAdapter(Context context, List<Bet> betList) {

        this.context = context;
        this.betList = betList;

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

        holder.idTextView.setText("id Match : " + itemBet.getId());
        holder.creditWageredTextView.setText("Crédits misés : " + itemBet.getCreditsWagered());
    }


}