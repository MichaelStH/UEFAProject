package fr.esgi.iam.uefa.adapter.article;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Article;

/**
 * Created by MichaelWayne on 21/08/2016.
 */
public class ArticleAdapter  extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList){

        this.context = context;
        this.articleList = articleList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView item_title, item_content;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view_item);

            item_title = (TextView) view.findViewById(R.id.title_item);
            item_content = (TextView) view.findViewById(R.id.content_item);
        }

        public void bind(Article itemArticle) {

            item_title.setText(itemArticle.getTitle());
            item_content.setText(itemArticle.getContent());
        }
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

        holder.bind( itemArticle );;
    }
}
