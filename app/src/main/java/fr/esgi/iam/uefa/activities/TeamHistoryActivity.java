package fr.esgi.iam.uefa.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Article;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 12/02/2016.
 */
public class TeamHistoryActivity extends AppCompatActivity {

    private static final String TAG = TeamHistoryActivity.class.getSimpleName();

    private Context mContext = null;

    private View rootView = null;
    private TextView homeTextViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_history);

        homeTextViewContent = (TextView) findViewById(R.id.team_history_content);

        final StringBuilder testBuilder = new StringBuilder();

        MyApplication.getUefaRestClient().getApiService().getArticles(new Callback<List<Article>>() {
            @Override
            public void success(List<Article> articles, Response response) {

                /*for (Article tempArticle : articles) {
                    testBuilder.append(tempArticle.getTitle()).append("\n").append(tempArticle.getContent()).append("\n");
                }

                homeTextViewContent.setText( Html.fromHtml(testBuilder.toString()) );*/

                //for (Article tempArticle : articles) {
                testBuilder.append(articles.get(0).getTitle()).append("\n").append(articles.get(0).getContent()).append("\n");
                //}

                homeTextViewContent.setText( Html.fromHtml(testBuilder.toString()) );
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });

    }
}
