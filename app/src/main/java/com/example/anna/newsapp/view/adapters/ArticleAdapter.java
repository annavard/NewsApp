package com.example.anna.newsapp.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.view.activities.MainActivity;
import com.example.anna.newsapp.view.view_holders.ArticleViewHolder;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private List<Article> mArticles;
    private ArticleViewHolder.ItemClickListener mListener;
    private boolean mIsHorisontal;


    public ArticleAdapter(List<Article> articles, ArticleViewHolder.ItemClickListener listener, boolean isHorisontal) {
        mArticles = articles;
        mListener = listener;
        mIsHorisontal = isHorisontal;
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (mIsHorisontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_horizontal, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        }

        return new ArticleViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.bindData(article);
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void updateData(List<Article> articles) {
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }
}
