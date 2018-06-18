package com.example.anna.newsapp.view.view_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.models.Result;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title)
    TextView titleText;

    @BindView(R.id.text_category)
    TextView categoryText;

    @BindView(R.id.img_thumbnail)
    ImageView thumbnailImage;

    public static String TAG = "ArticleViewHolder";


    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Result result) {
        titleText.setText(result.getWebTitle());
        categoryText.setText(result.getSectionName());
        Log.d(TAG, result.getSectionName());

        Picasso.get()
                .load(result.getFields().getThumbnail())
                .placeholder(R.drawable.placeholder)
                .into(thumbnailImage);
    }
}
