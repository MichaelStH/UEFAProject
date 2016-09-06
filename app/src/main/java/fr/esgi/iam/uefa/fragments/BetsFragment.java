package fr.esgi.iam.uefa.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.helper.RetrofitHelper;
import fr.esgi.iam.uefa.model.Bet;
import fr.esgi.iam.uefa.model.BetResponse;
import fr.esgi.iam.uefa.model.Match;
import fr.esgi.iam.uefa.model.Team;
import fr.esgi.iam.uefa.model.UserResponse;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 23/08/2016.
 */
public class BetsFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //TAG & Context
    private static final String TAG = BetsFragment.class.getSimpleName();
    public static BetsFragment instance;
    public static Context mContext = null;

    //Views
    private static Spinner teamMatchSpinner;
    private Spinner teamScore1Spinner;
    private Spinner teamScore2Spinner;
    private EditText moneyEditText;
    private TextView creditsWageredTextView;
    private Button betButton;

    private static List<Match> matchesList = null;
    private static List<Team> teamsList = null;

    private int idMatch = 0, scoreTeam1 = 0, scoreTeam2 = 0, creditsWagered = 0;
    private String szIdMatch = "", userToken = "";

    private ArrayList<String> matchesInfosList;


    public static Fragment newInstance(){
        BetsFragment fragment = new BetsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        instance = BetsFragment.this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bets, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected(mContext) ) {

            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );
            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

        }
        else {
            loadAvailableMatches();
            getUserCredits();
        }
    }


    private void initViews(View view){

        teamMatchSpinner = (Spinner) view.findViewById(R.id.team_bet_team_match);
        teamScore1Spinner = (Spinner) view.findViewById(R.id.team_bet_score_team_1_spinner);
        teamScore2Spinner = (Spinner) view.findViewById(R.id.team_bet_score_team_2_spinner);

        moneyEditText = (EditText) view.findViewById(R.id.team_bet_money_editText);

        creditsWageredTextView = (TextView) view.findViewById(R.id.team_bet_credits_wagered_textView);

        betButton = (Button) view.findViewById(R.id.team_bet_button);

        if ( betButton != null )
            betButton.setOnClickListener( BetsFragment.this );
    }

    private void getUserCredits(){
        SharedPreferences sharedPref = mContext.getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        String userToken = sharedPref.getString( MyApplication.USER_TOKEN_ARG, "" );

        MyApplication.getUefaRestClient().getApiService().isUserConnected(userToken, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if ( ! ( 200 == response.getStatus() ) ){
                    Log.e( TAG, "Another code occurred : " + response.getStatus());
                }else{
                    creditsWageredTextView.setText(userResponse.getUser().getCredits());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    /**
     * Make REST call to get matches and teams
     */
    public void loadAvailableMatches(){
        RetrofitHelper.getTeams((BetsFragment) instance);
        RetrofitHelper.getMatches((BetsFragment) instance);
    }

    public void onDataLoaded( List<Match> matches, List<Team> teams ){

        matchesList = matches;
        teamsList = teams;

        matchesInfosList = getAvailableMatches();

        if ( matchesInfosList != null && matchesInfosList.size() > 0 ){
            Log.e("OHOH", "this is good");

            teamMatchSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, matchesInfosList));
            teamMatchSpinner .setOnItemSelectedListener( this );
        }
    }

    /**
     * Old code
     *
     boolean canBetOnMatch( String szCurrentDate, String szMatchDate ) {

     // Doivent être sous format 2016-01-30
     szMatchDate = szMatchDate.replaceAll( "-", "" );
     szCurrentDate = szCurrentDate.replaceAll( "-", "" );

     return ( Integer.parseInt( szCurrentDate ) <= Integer.parseInt( szMatchDate ) );
     }
     */

    public ArrayList<String> getAvailableMatches(  ) {
        String szTeam1, szTeam2;
        ArrayList<String> matchesAvailable = null;

        Calendar currentCalendar = Calendar.getInstance( );
        Calendar matchCalendar = Calendar.getInstance( );
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Date currentDate = new Date( );
        String szMatchDate = null;

        currentCalendar.setTime( currentDate );

        try {

            for( Match match : matchesList ) {
                matchCalendar.setTime( dateFormat.parse( match.getDate() ) );

                if(     matchCalendar.get( Calendar.YEAR ) == currentCalendar.get( Calendar.YEAR ) &&
                        matchCalendar.get( Calendar.DAY_OF_YEAR ) >= currentCalendar.get( Calendar.DAY_OF_YEAR ) ) {

                    szTeam1 = retrieveTeamName( teamsList, match.getIdTeam1() );
                    szTeam2 = retrieveTeamName( teamsList, match.getIdTeam2() );

                    if( matchesAvailable == null ) {
                        matchesAvailable = new ArrayList<String>();
                    }

                    matchesAvailable.add( match.getId() + ". " +  szTeam1 + " VS " + szTeam2 + " (" + match.getDate() + " " + match.getTime() + ")" );
                }
            }
        } catch (ParseException e) {
            e.printStackTrace( );
        }

        return matchesAvailable;
    }

    public static String retrieveTeamName( List<Team> teams, int idTeam ) {
        String szTeamName = null;

        for( Team team : teams ) {
            if( team.getId() == idTeam ) {
                szTeamName = team.getName();
                break;
            }
        }
        return szTeamName;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.team_bet_team_match:

                szIdMatch = String.valueOf(teamMatchSpinner.getSelectedItem().toString().charAt(0));
                Log.e(TAG, "Test id : " + szIdMatch);
                idMatch = Integer.valueOf( szIdMatch );
                break;

            case R.id.team_bet_score_team_1_spinner:
                scoreTeam1 = Integer.valueOf( teamScore1Spinner.getSelectedItem().toString() );
                break;

            case R.id.team_bet_score_team_2_spinner:
                scoreTeam2 = Integer.valueOf( teamScore2Spinner.getSelectedItem().toString() );
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

//        Log.e(TAG, "button click");

        SharedPreferences sharedPref = mContext.getSharedPreferences( MyApplication.USER_SHARED_PREFS_TAG, Context.MODE_PRIVATE );
        userToken = sharedPref.getString( MyApplication.USER_TOKEN_ARG, "" );

        if ( moneyEditText.getText().toString().isEmpty() ){
            moneyEditText.setError("Ce champs ne peut pas être vide");
        }else{

            creditsWagered = Integer.valueOf(moneyEditText.getText().toString());

            MyApplication.getUefaRestClient().getApiService().createBet(userToken, idMatch, creditsWagered, scoreTeam1, scoreTeam2, new Callback<BetResponse>() {
                @Override
                public void success(BetResponse betResponse, Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{

                        if ( null != betResponse.getError() ) {
                            Log.e(TAG, "Error : " + betResponse.getError());
                            Toast.makeText(mContext, betResponse.getError(), Toast.LENGTH_SHORT).show();
                        } else {

                            Bet bet = betResponse.getBets();

                            if (null != bet)
                                Log.e(TAG, "Result : " + bet.getCreditsWagered());

                            Utils.showActionInToast( mContext, "Votre pari a bien été enregistré" );
                        }

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }

    }
}
