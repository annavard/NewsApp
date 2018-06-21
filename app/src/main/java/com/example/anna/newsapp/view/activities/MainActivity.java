package com.example.anna.newsapp.view.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.view.adapters.ArticleAdapter;
import com.example.anna.newsapp.view_model.ArticleViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_pageing)
    ProgressBar mProgressBar;

    private ArticleAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "MainActivity";
    private ArticleViewModel mArticleViewModel;
    private List<Result> mArticles;
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        mArticleViewModel.getArticleList().observe(this, articles -> {
            Log.d(TAG, "List<Result> onChanged");
            mArticles = articles;
            if(isFirstLoad){
                initAdapter();
                isFirstLoad = false;
            }else{
                mAdapter.updateData(mArticles);
            }

        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled - lastPosition: " + lastPosition);
                if (lastPosition == mLayoutManager.getItemCount() - 1) {
                    Log.d(TAG, "onScrolled - End of list?");
                    loadData();
                }
            }
        });

    }


    private void loadData() {
        Log.d(TAG, "loadData");
        if (mArticleViewModel == null) return;
        mProgressBar.setVisibility(View.VISIBLE);
        mArticleViewModel.getArticleList();
    }

    public void initAdapter() {
        mAdapter = new ArticleAdapter(mArticles);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
