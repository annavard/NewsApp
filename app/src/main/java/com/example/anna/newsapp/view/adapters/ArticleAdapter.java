package com.example.anna.newsapp.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public static String TAG = "ArticleAdapter";
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
        Log.d(TAG, "onCreateViewHolder horisontal: " + mIsHorisontal);
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
        Log.d(TAG, "onBindViewHolder - position: " + position);
        Log.d(TAG, "onBindViewHolder - pinned: " + article.getPinned());
        holder.bindData(article);
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void updateData(List<Article> articles) {
        Log.d(MainActivity.TAG, "updateData");
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }


    public void addItem(Article article) {
        Log.d(TAG, "addItem");
        mArticles.add(article);
        int position = mArticles.indexOf(article);
        notifyItemInserted(position);
    }

    public void removeItem(Article article) {
        Log.d(TAG, "removeItem");
        int position = mArticles.indexOf(article);
        mArticles.remove(article);
        notifyItemRemoved(position);
    }

    public void updateItem(Article article) {
        Log.d(TAG, "updateItem");

        int position = mArticles.indexOf(article);
        Log.d(TAG, "pinStateChangedUpdate - position: " + position);
        Log.d(TAG, "pinStateChangedUpdate - pinned  - BEFORE: " + mArticles.get(position).getPinned());
        Article oldValue = mArticles.get(position);
        oldValue.setPinned(!oldValue.getPinned());
        mArticles.set(position, oldValue);
        Log.d(TAG, "pinStateChangedUpdate - pinned  - AFTER: " + mArticles.get(position).getPinned());
        notifyItemChanged(position);
    }
}
