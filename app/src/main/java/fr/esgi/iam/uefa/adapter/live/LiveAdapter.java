package fr.esgi.iam.uefa.adapter.live;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.players.PlayerListAdapter;
import fr.esgi.iam.uefa.model.Action;

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

        public TextView comment;

        public ViewHolder(View view) {
            super(view);

            comment = (TextView) view.findViewById(R.id.team_live_event_text_description);
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

        holder.comment.setText(itemAction.getComment());
    }

}
