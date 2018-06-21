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
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.view.adapters.ArticleAdapter;
import com.example.anna.newsapp.view_model.ArticleViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_main)
    ProgressBar mProgressBar;

    ArticleAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private static final String TAG = "MainActivity";
    private ArticleViewModel mArticleViewModel;
    private List<Result> mArticles;
    private List<Photo> mPhotos;
    private boolean mIsLoading;
    private boolean mIsInitialCall = true;
    private int mPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
//        mLiveArticles = loadData();
        mArticleViewModel.getPhotos().observe(this, photos -> {
            Log.d(TAG, "onChanged!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            mProgressBar.setVisibility(View.GONE);
            mPhotos = photos;
            if (mIsInitialCall) {
                initiateAdapter();
                mIsInitialCall = false;
            } else {
                mAdapter.updateList(mPhotos.subList(mPageNumber, mPageNumber + 10));
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d(TAG, "onScrolled - lastPosition: " + lastPosition);
                if (lastPosition == mLayoutManager.getItemCount() - 1) {
                    Log.d(TAG, "onScrolled - End of list?");
                    loadData();
                }
            }
        });

    }


    private void initiateAdapter() {
        List<Photo> shrinkedList = mPhotos.subList(mPageNumber, mPageNumber + 10);
        mAdapter = new ArticleAdapter(shrinkedList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadData() {
        Log.d(TAG, "loadData");
        if (mArticleViewModel == null) return;
        mPageNumber += 10;
        mProgressBar.setVisibility(View.VISIBLE);
         mArticleViewModel.getPhotos();
    }
}
