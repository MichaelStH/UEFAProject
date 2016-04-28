package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.TeamSelectionGridAdapter;
import fr.esgi.iam.uefa.app.MyApplication;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamSelectionActivity extends AppCompatActivity {

    private static final String TAG = TeamSelectionActivity.class.getSimpleName();


    private Context mContext;

    private GridView mGridView;
    private TeamSelectionGridAdapter mContryAdapter;

    private String [] countriesName;
    private int [] countryFlags = {
            R.drawable.albania,
            R.drawable.germany,
            R.drawable.england,
            R.drawable.austria,
            R.drawable.belgium,
            R.drawable.croatia,
            R.drawable.spain,
            R.drawable.france,
            R.drawable.hungary,
            R.drawable.northern_ireland,
            R.drawable.island,
            R.drawable.italy,
            R.drawable.wales,
            R.drawable.poland,
            R.drawable.portugal,
            R.drawable.ireland,
            R.drawable.czech_republic,
            R.drawable.roumania,
            R.drawable.russia,
            R.drawable.slovakia,
            R.drawable.sweden,
            R.drawable.switzerland,
            R.drawable.turkey,
            R.drawable.ukraine
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        mContext = this;
        countriesName = getApplicationContext().getResources().getStringArray(R.array.countries);

        mGridView = (GridView) findViewById(R.id.team_selection_grid_view);

        mContryAdapter = new TeamSelectionGridAdapter(mContext, countriesName, countryFlags);
        mGridView.setAdapter(mContryAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Intent intent = new Intent(mContext, TeamHomeActivity.class);

                    //int resourceId = mContext.getResources().getIdentifier(String.valueOf(((ImageView) view.findViewById(R.id.imageView_team_nation)).getTag()), "drawable", mContext.getPackageName());
                    //String name = getResources().getResourceEntryName(countryFlags[position]);

                    //Retrieve Name of the country
                    String itemCountryName = (String) ((TextView) view.findViewById(R.id.textView_team_nation)).getText();

                    //Convert the flag into a bitmap
                    Bitmap bitmapToEncode = BitmapFactory.decodeResource(getResources(), countryFlags[position]);
                    //Encode the bitmap
                    String itemCountryFlagsId = encodeToBase64(bitmapToEncode );

                    //Log.e( TAG, "Equipe : " + itemCountryName );

                    //Build the bundle to send to the other activity
                    intent.putExtra( MyApplication.TEAM_NATION_NAME_ARG, itemCountryName );
                    intent.putExtra( MyApplication.TEAM_NATION_FLAG_ARG, itemCountryFlagsId );

                    //Save in Shared Prefs
                    saveInSharedPrefs( itemCountryName, itemCountryFlagsId );

                    //Launch Home Activity
                    startActivity(intent);
                }
                catch (Exception e){
                    Log.e( TAG, e.getMessage() );
                }

            }
        });
    }

    /**
     * Method to encode a Bitmap into string base64
     * @param image
     * @return
     */
    public static String encodeToBase64(Bitmap image) {

        Bitmap bitmapImage = image;
        ByteArrayOutputStream mByteArrayOS = new ByteArrayOutputStream();

        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, mByteArrayOS);
        byte[] mByte = mByteArrayOS.toByteArray();
        String imageEncoded = Base64.encodeToString(mByte, Base64.DEFAULT);

        //Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    /**
     * Pass myBitmap inside this method like something encodeTobase64 in my preference
     * @param countryName
     * @param countryFlag
     */
    public void saveInSharedPrefs(String countryName, String  countryFlag){

        SharedPreferences myPrefrence = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = myPrefrence.edit();

        editor.putBoolean( MyApplication.TEAM_IS_CHOSEN_ARG, true );

        editor.putString( MyApplication.TEAM_NATION_NAME_ARG, countryName );
        editor.putString( MyApplication.TEAM_NATION_FLAG_ARG, countryFlag );
        editor.commit();

    }
}
