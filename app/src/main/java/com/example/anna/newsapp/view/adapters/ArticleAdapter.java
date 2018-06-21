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
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.view.view_holders.ArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
//    private List<Result> mResultList;
    private List<Photo> mResultList;


//    public ArticleAdapter(List<Result> resultList) {
//        mResultList = resultList;
//    }

    public ArticleAdapter(List<Photo> resultList) {
        mResultList = resultList;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
//        Result result = mResultList.get(position);
        Photo result = mResultList.get(position);
        holder.bindData(result);
    }


    @Override
    public int getItemCount() {
        return mResultList.size();
    }

//    public void updateList(List<Result> newList){
//        mResultList = newList;
//        notifyDataSetChanged();
//    }

    public void updateList(List<Photo> newList){
        mResultList = newList;
        notifyDataSetChanged();
    }
}
