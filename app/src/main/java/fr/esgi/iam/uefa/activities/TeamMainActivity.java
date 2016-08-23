package fr.esgi.iam.uefa.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.fragments.ArticleFragment;
import fr.esgi.iam.uefa.fragments.BetsFragment;
import fr.esgi.iam.uefa.fragments.HistoryFragment;
import fr.esgi.iam.uefa.fragments.LiveFragment;
import fr.esgi.iam.uefa.fragments.PlayersFragment;
import fr.esgi.iam.uefa.fragments.ProfileFragment;
import fr.esgi.iam.uefa.fragments.RankingFragment;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.model.User;
import fr.esgi.iam.uefa.rest.UefaRestClient;
import fr.esgi.iam.uefa.utils.Utils;

/**
 * Created by MichaelWayne on 21/08/2016.
 */
public class TeamMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = TeamMainActivity.class.getSimpleName();
    private Context mContext = null;

    private Button disconnectionButton, changeTeamButton;
    private ImageView mImgHome = null;
    private TextView mTvHome = null;

    private Team bundle_team;

    public static final String USERNAME_ARG = "username";
    private User bundle_user = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mImgHome = (ImageView) findViewById(R.id.mainThumbImageView);
        mTvHome = (TextView) findViewById(R.id.mainTeamTextView);

        if (savedInstanceState == null){

            Fragment fragment = ArticleFragment.newInstance();

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        try {
            fillNavigationViewHeader();
        }catch (NullPointerException|IllegalStateException exception){
            exception.printStackTrace();
        }


        navigationView.getMenu().getItem(0).setChecked(true);
    }


    private void fillNavigationViewHeader() throws NullPointerException, IllegalStateException{

        if ( mImgHome != null && mTvHome != null )
            Log.e("OHOH", "Test views : " + mImgHome.toString() +  " | " + mTvHome.toString() );

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            Log.e(TAG, "Bundle null - Read in SharedPrefs");

            SharedPreferences sharedPref = getSharedPreferences( MyApplication.TEAM_SHARED_PREFS_TAG, Context.MODE_PRIVATE );

            Gson gson = new Gson();
            String json = sharedPref.getString( MyApplication.TEAM_NATION_ARG, "" );
            bundle_team = gson.fromJson(json, Team.class);


            Log.e("OHOH", "Debug : " + bundle_team.getName());

            if (bundle_team != null){
                mTvHome.setText(bundle_team.getName());
                Picasso.with(this)
                        .load(UefaRestClient.BASE_ENDPOINT + "/" + bundle_team.getFlag())
                        .into(mImgHome);
            }

            return;
        }
        else
        {

            bundle_team = extras.getParcelable( MyApplication.TEAM_NATION_ARG );

            //Debug check
            Log.e(TAG, "Bundle not null");


            //Fill the views
            mTvHome.setText( bundle_team.getName() );
            Picasso.with(this)
                    .load(UefaRestClient.BASE_ENDPOINT + "/" + bundle_team.getFlag())
                    .into(mImgHome);

            Log.d(TAG, "Bundle Image loaded");

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            // Handle the news action
            Log.i( TAG, "call news Fragment" );
            fragment = ArticleFragment.newInstance();

        } else if (id == R.id.nav_profile) {
            Log.i( TAG, "call profile Fragment" );
            fragment = ProfileFragment.newInstance( bundle_team );

        } else if (id == R.id.nav_history) {
            Log.i( TAG, "call history Fragment" );
            fragment = HistoryFragment.newInstance( bundle_team );

        } else if (id == R.id.nav_players) {
            Log.i( TAG, "call players Fragment" );
            fragment = PlayersFragment.newInstance( bundle_team );

        } else if (id == R.id.nav_ranking) {
            Log.i( TAG, "call ranking Fragment" );
            fragment = RankingFragment.newInstance();

        } else if (id == R.id.nav_live) {
            Log.i( TAG, "call live Fragment" );
            fragment = LiveFragment.newInstance();

        } else if (id == R.id.nav_bets) {
            Log.i( TAG, "call bets Fragment" );
            fragment = BetsFragment.newInstance();

        } else if (id == R.id.nav_disconnect) {
            Log.i( TAG, "Disconnect user" );
            showAlertDialog();
            return true;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Build an alert dialog to ask the user if he wants to disconnect his account from the app
     */
    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( TeamMainActivity.this );

        // set title
        alertDialogBuilder.setTitle("Déconnexion");

        // set dialog message
        alertDialogBuilder
                .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        eraseSharedData();
                        Utils.LaunchActivity(mContext, LoginActivity.class);
                        TeamMainActivity.this.finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Erase data previously saved in shared preferences (token and uid)
     */
    private void eraseSharedData(){
        SharedPreferences sharedPref = getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString( MyApplication.USER_UID_ARG, "" );
        editor.putString( MyApplication.USER_TOKEN_ARG, "" );
        editor.apply();
    }
}
