package fr.esgi.iam.uefa.adapter.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Article;

/**
 * Created by MichaelWayne on 21/08/2016.
 */
public class ArticleAdapter  extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList){

        this.context = context;
        this.articleList = articleList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemReposRow = inflater.inflate(R.layout.team_article_item_row, parent, false );

        return new ViewHolder(itemReposRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Article itemArticle = articleList.get( position );

        holder.bind( itemArticle );
    }
}
