package fr.esgi.iam.uefa.adapter.players;

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
import fr.esgi.iam.uefa.model.Player;

/**
 * Created by MichaelWayne on 01/05/2016.
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder>{

    private static final String TAG = PlayerListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private List<Player> playerList;

    public PlayerListAdapter(Context context, List<Player> playerList){

        this.context = context;
        this.playerList = playerList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, firstname, number, age, field;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_team_players_name);
            firstname = (TextView) view.findViewById(R.id.item_team_players_firstname);
            number = (TextView) view.findViewById(R.id.item_team_players_number);
            age = (TextView) view.findViewById(R.id.item_team_players_age);
            field = (TextView) view.findViewById(R.id.item_team_players_position);
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
        View itemPlayerRow = inflater.inflate(R.layout.team_players_item_row, parent, false );

        return new ViewHolder(itemPlayerRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Player itemPlayer = playerList.get( position );

        holder.name.setText(itemPlayer.getName());
        holder.firstname.setText(itemPlayer.getFirstName());
        holder.number.setText(itemPlayer.getBackNumber());
        holder.age.setText( formatAge( itemPlayer.getAge() ) );
        holder.field.setText(itemPlayer.getPlayerPosition());
    }


    public static String formatAge(String dateOfBirth) {

        DateFormat formatter = null;
        Date date = null;

        try{
            formatter = new SimpleDateFormat("yyyy-MM-DD");
            date = (Date) formatter.parse(dateOfBirth);
        } catch (ParseException e){
            Log.e( TAG, e.getMessage());
        }

        return String.valueOf(  getAge( date ) );
    }


    /**
     * This Method is unit tested properly for very different cases ,
     * taking care of Leap Year days difference in a year,
     * and date cases month and Year boundary cases (12/31/1980, 01/01/1980 etc)
     **/

    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than today's date (after 2 days adjustment of leap year) then decrement age one year
        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

            // If birth date and today's date are of same month and birth day of month is greater than today's day of month then decrement age
        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }

}
