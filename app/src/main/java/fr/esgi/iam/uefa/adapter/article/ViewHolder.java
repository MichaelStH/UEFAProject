package fr.esgi.iam.uefa.adapter.article;

import android.animation.ValueAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.Article;

/**
 * Created by MichaelWayne on 25/08/2016.
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private int originalHeight = 0;
    private boolean mIsViewExpanded = false;

    public CardView cardView;
    public TextView item_title, item_content;

    public ViewHolder(View view) {
        super(view);

        cardView = (CardView) view.findViewById(R.id.card_view_item);

        item_title = (TextView) view.findViewById(R.id.title_item);
        item_content = (TextView) view.findViewById(R.id.content_item);

        view.setOnClickListener(this);

        // Initialize other views, like TextView, ImageView, etc. here

        // If mIsViewExpanded == false then set the visibility
        // of whatever will be in the expanded to GONE

        if (mIsViewExpanded == false) {
            // Set Views to View.GONE and .setEnabled(false)
            item_content.setVisibility(View.GONE);
            item_content.setEnabled(false);
        }
    }

    public void bind(Article itemArticle) {
        item_title.setText(itemArticle.getTitle());
        item_content.setText(Html.fromHtml( itemArticle.getContent() ) );
    }

    @Override
    public void onClick(final View view) {

        // If the originalHeight is 0 then find the height of the View being used
        // This would be the height of the cardview
        if (originalHeight == 0) {
            originalHeight = view.getHeight();
        }

        // Declare a ValueAnimator object
        ValueAnimator valueAnimator;
        if (!mIsViewExpanded) {
            item_content.setVisibility(View.VISIBLE);
            item_content.setEnabled(true);
            mIsViewExpanded = true;
            valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + (int) (originalHeight * 11.0)); // These values in this method can be changed to expand however much you like
        } else {
            mIsViewExpanded = false;
            valueAnimator = ValueAnimator.ofInt(originalHeight + (int) (originalHeight * 11.0), originalHeight);

            Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out

            a.setDuration(200);
            // Set a listener to the animation and configure onAnimationEnd
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    item_content.setVisibility(View.INVISIBLE);
                    item_content.setEnabled(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // Set the animation on the custom view
            item_content.startAnimation(a);
        }
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            }
        });


        valueAnimator.start();

    }
}