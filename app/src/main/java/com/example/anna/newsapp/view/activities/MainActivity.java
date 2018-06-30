package com.example.anna.newsapp.view.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.ArticleDataHolder;
import com.example.anna.newsapp.model.DummyData;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.view.adapters.ArticleAdapter;
import com.example.anna.newsapp.view.view_holders.ArticleViewHolder;
import com.example.anna.newsapp.view_model.ArticleViewModel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ArticleViewHolder.ItemClickListener {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE = 1;
    public static final String ARTICLE_KEY = "article_key";

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.recycler_horizontal)
    RecyclerView mRecyclerHorizontal;

    @BindView(R.id.progress_pageing)
    ProgressBar mProgressBar;

    @BindView(R.id.progress_main)
    ProgressBar mProgressBarMain;

    private ArticleAdapter mAdapter;
    private ArticleAdapter mAdapterHorizontal;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManagerHorizontal;
    private ArticleViewModel mArticleViewModel;
    private List<Article> mArticles;
    private List<Article> mPinnedArticles;
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

            if (isFirstLoad) {
                initAdapter();
                initHorizontalAdapter();
                isFirstLoad = false;
            } else {
                mRecyclerView.post(() -> {
                    mAdapter.updateData(mArticles);
                });
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d(TAG, "onScrolled - lastPosition: " + lastPosition);
                Log.d(TAG, "onScrolled - lastPosition: " + lastPosition);
                Log.d(TAG, "onScrolled - isLoading: " + mIsLoading);
                if (!mIsLoading && lastPosition == mLayoutManager.getItemCount() - 1) {
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
        mIsLoading = true;
        mPageNumber++;
        mArticleViewModel.getArticleList(mPageNumber);
    }

    public void initAdapter() {
        Log.d(TAG, "initAdapter");
        mAdapter = new ArticleAdapter(mArticles, this, false);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void initHorizontalAdapter() {
        Log.d(TAG, "initHorizontalAdapter");
        mPinnedArticles = new ArrayList<>();
        mAdapterHorizontal = new ArticleAdapter(mPinnedArticles, this::itemClicked, true);
        mRecyclerHorizontal.setAdapter(mAdapterHorizontal);
        mLayoutManagerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerHorizontal.setLayoutManager(mLayoutManagerHorizontal);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void itemClicked(Article article, ImageView imageView, TextView sectionText, TextView titleText) {
        Log.d(TAG, "RecyclerView itemClicked");
        Intent intent = new Intent(this, DetailsActivity.class);
        Parcelable wrappedResult = Parcels.wrap(article);
        intent.putExtra(ARTICLE_KEY, wrappedResult);

        Pair sectionAnim = Pair.create(sectionText, "category_transition");
        Pair titleAnim = Pair.create(titleText, "title_transition");
        Pair imageAnim = Pair.create(imageView, "details_transition");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sectionAnim, titleAnim, imageAnim);
        startActivityForResult(intent, REQUEST_CODE, options.toBundle());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Article pinned = Parcels.unwrap(data.getParcelableExtra(ARTICLE_KEY));
                Log.d(TAG, "onActivityResult pinned - uid: " + pinned.getUid());
                Log.d(TAG, "onActivityResult pinned - title: " + pinned.getWebTitle());
                Log.d(TAG, "onActivityResult pinned: " + pinned.getPinned());
                pinStateChangedUpdate(pinned);
            }
        }


    }

    private void pinStateChangedUpdate(Article pinned) {
        Log.d(TAG, "pinStateChangedUpdate");

        if (pinned.getPinned()) {
            mAdapterHorizontal.addItem(pinned);
        } else if (mPinnedArticles.size() > 0) {
            mAdapterHorizontal.removeItem(pinned);
        }



        mAdapter.updateItem(pinned);
    }


}

