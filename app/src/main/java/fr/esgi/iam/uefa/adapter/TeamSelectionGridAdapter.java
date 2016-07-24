package fr.esgi.iam.uefa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.rest.UefaRestClient;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamSelectionGridAdapter extends BaseAdapter{

    private static final String TAG = TeamSelectionGridAdapter.class.getSimpleName();

    private Context mContext;

    /*
    private String [] result;
    private int [] imageId;
    */

    private List<Team> teamList;

    private static LayoutInflater inflater=null;

    //Old constructor
    public TeamSelectionGridAdapter(Context context, String [] countryList, int [] imagesList){

        mContext = context;

        /*
        result = countryList;
        imageId = imagesList;
        */

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //Constructor using Rest
    public TeamSelectionGridAdapter(Context context, ArrayList<Team> teamList){

        this.mContext = context;
        this.teamList = teamList;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        //return result.length;
        return teamList.size();
    }

    @Override
    public Object getItem(int position) {
        //return position;
        return teamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder holder;

        if (convertView == null){
            rowView = inflater.inflate(R.layout.team_selection_grid_item, null);

            holder = new ViewHolder();

            holder.countryName=(TextView) rowView.findViewById(R.id.textView_team_nation);
            holder.countryImg=(ImageView) rowView.findViewById(R.id.imageView_team_nation);

            rowView.setTag( holder );
        }
        else{
            holder = (ViewHolder) rowView.getTag();
        }

        Log.e(TAG,"test : " +teamList.get(position).getName() + " ----- " + teamList.get(position).getFlag());

        holder.countryName.setText(teamList.get(position).getName());
        //holder.countryImg.setImageResource(imageId[position]);
        Picasso.with(mContext)
                .load( UefaRestClient.BASE_ENDPOINT + "/" + teamList.get(position).getFlag() )
                .into(holder.countryImg);

        return rowView;
    }

    public class ViewHolder{

        TextView countryName;
        ImageView countryImg;

    }

}
