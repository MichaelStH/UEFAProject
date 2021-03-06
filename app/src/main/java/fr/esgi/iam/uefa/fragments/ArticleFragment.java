package fr.esgi.iam.uefa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.adapter.article.ArticleAdapter;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Article;
import fr.esgi.iam.uefa.utils.DeviceManagerUtils;
import fr.esgi.iam.uefa.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by MichaelWayne on 21/08/2016.
 */
public class ArticleFragment extends Fragment {

    private static final String TAG = ArticleFragment.class.getSimpleName();
    private Context mContext;

    private View rootView;
    private ProgressBar mLoader;
    private RecyclerView contentRecyclerView;
    private ArticleAdapter articleAdapter;

    public static Fragment newInstance(){
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        //Test the internet's connection
        if( !DeviceManagerUtils.isConnected( mContext ) ) {

            Utils.dismissLoader(mLoader);
            Utils.showActionInToast(mContext, mContext.getResources().getString( R.string.no_internet_connection ) );

            getLayoutInflater(savedInstanceState).inflate(R.layout.no_connection_layout, (ViewGroup) view);

        }
        else {
            MyApplication.getUefaRestClient().getApiService().getArticles(new Callback<List<Article>>() {
                @Override
                public void success(List<Article> articles, retrofit.client.Response response) {
                    if ( ! ( 200 == response.getStatus() ) ){
                        Log.e( TAG, "Another code occurred : " + response.getStatus());
                    }else{
                        List<Article> articlesList = articles;

                        //create the adapter
                        articleAdapter = new ArticleAdapter(mContext, articlesList);

                        //Set properties for the RecyclerView
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        contentRecyclerView.setLayoutManager(mLayoutManager);
                        contentRecyclerView.setItemAnimator(new DefaultItemAnimator());

                        Utils.dismissLoader(mLoader);

                        if ( contentRecyclerView != null && !contentRecyclerView.isInLayout()){
                            contentRecyclerView.setVisibility(View.VISIBLE);
                        }

                        contentRecyclerView.setAdapter(articleAdapter);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    private void initViews(View view){
        contentRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycler_view);
        mLoader = (ProgressBar) view.findViewById(R.id.article_loader);
    }
}
