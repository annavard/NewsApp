package com.example.anna.newsapp.view.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.view.view_holders.ArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends PagedListAdapter<Result, ArticleViewHolder> {
    private List<Result> mResultList;
    private Context mContext;

    public ArticleAdapter(@NonNull DiffUtil.ItemCallback<Result> diffCallback) {
        super(diffCallback);
    }

//    public ArticleAdapter(List<Result> resultList, Context context){
//        mResultList = new ArrayList<>();
//        mResultList = resultList;
//        mContext = context;
//    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Result result = getItem(position);
        if (result == null)
            return;
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }
}
