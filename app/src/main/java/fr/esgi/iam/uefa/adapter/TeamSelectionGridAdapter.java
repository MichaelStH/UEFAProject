package fr.esgi.iam.uefa.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.esgi.iam.uefa.R;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamSelectionGridAdapter extends BaseAdapter{

    private Context mContext;

    private String [] result;
    private int [] imageId;

    private static LayoutInflater inflater=null;

    public TeamSelectionGridAdapter(Context context, String [] countryList, int [] imagesList){

        mContext = context;

        result = countryList;
        imageId = imagesList;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        View rowView;

        rowView = inflater.inflate(R.layout.team_selection_grid_item, null);

        holder.countryName=(TextView) rowView.findViewById(R.id.textView_team_nation);
        holder.countryImg=(ImageView) rowView.findViewById(R.id.imageView_team_nation);

        holder.countryName.setText(result[position]);
        holder.countryImg.setImageResource(imageId[position]);

//        rowView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                //Toast.makeText(mContext, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });

        return rowView;
    }

    public class ViewHolder{

        TextView countryName;
        ImageView countryImg;

    }

}
