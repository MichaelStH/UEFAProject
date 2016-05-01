package fr.esgi.iam.uefa.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.esgi.iam.uefa.rest.service.UefaApiService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by MichaelWayne on 28/04/2016.
 */
public class UefaRestClient {

    private static final String TAG = UefaRestClient.class.getSimpleName();

    //BASE_ENDPOINT to fetch
    private static final String BASE_ENDPOINT = "http://dylan.absil.pro/projet/api";

    public UefaApiService apiService;

    /**
     * Constructor
     */
    public UefaRestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(UefaApiService.class);

        Log.i(TAG, "Construct");
    }

    public UefaApiService getApiService(){
        Log.i(TAG, "Method public YoutubeVideoApiService getApiService()");
        return apiService;
    }
}
