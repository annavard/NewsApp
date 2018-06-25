package com.example.anna.newsapp.view.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.models.Result;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title)
    TextView titleText;

    @BindView(R.id.text_category)
    TextView categoryText;

    @BindView(R.id.img_thumbnail)
    ImageView thumbnailImage;

    public static String TAG = "ArticleViewHolder";

    private ItemClickListener mListener;

    private Article mArticle;


    public ArticleViewHolder(View itemView, ItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    public void bindData(Article article) {
        mArticle = article;
        if (article.getWebTitle() != null) {
            titleText.setText(article.getWebTitle());
        }

        if (article.getSectionName() != null) {
            categoryText.setText(article.getSectionName());
        }

        if (article.getThumbnail() != null) {
            Picasso.get()
                    .load(article.getThumbnail())
                    .placeholder(R.drawable.placeholder)
                    .into(thumbnailImage);
        }
    }

    @OnClick(R.id.cardView)
    void articleItemClicked(){
        mListener.itemClicked(mArticle, thumbnailImage, categoryText, titleText);
    }

    public interface ItemClickListener {
        void itemClicked(Article article, ImageView imageView, TextView sectionText, TextView titleText);
    }
}
