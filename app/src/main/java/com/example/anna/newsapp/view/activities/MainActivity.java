package com.example.anna.newsapp.view.activities;

import android.arch.lifecycle.LiveData;
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
import com.example.anna.newsapp.model.repository.ArticleRepository;
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

    @BindView(R.id.progress_main)
    ProgressBar mProgressBarMain;

    private ArticleAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "MainActivity";
    private ArticleViewModel mArticleViewModel;
    private List<Result> mArticles;
    private boolean isFirstLoad = true;
    private boolean mIsLoading;
    private int mPageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBarMain.setVisibility(View.VISIBLE);
        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        mArticleViewModel.getArticleList(mPageNumber).observe(this, articles -> {
            Log.d(TAG, "List<Result> onChanged!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            mProgressBar.setVisibility(View.GONE);
            mProgressBarMain.setVisibility(View.GONE);
            mIsLoading = false;
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
                int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d(TAG, "onScrolled - lastPosition: " + lastPosition);
                if (!mIsLoading && lastPosition == mLayoutManager.getItemCount() - 2) {
                    Log.d(TAG, "onScrolled - End of list?");
                    loadData();
                    mIsLoading = true;
                }
            }
        });

    }


    private void loadData() {
        Log.d(TAG, "loadData");
        if (mArticleViewModel == null) return;
        mProgressBar.setVisibility(View.VISIBLE);
        mPageNumber++;
        mArticleViewModel.getArticleList(mPageNumber);
    }

    public void initAdapter() {
        mAdapter = new ArticleAdapter(mArticles);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


}
