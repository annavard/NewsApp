package com.example.anna.newsapp.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    ArticleAdapter mAdapter;
    private static final String TAG = "MainActivity";
    private ArticleViewModel mArticleViewModel;
    private LiveData<PagedList<Result>> mPagedArticles;
    private List<Result> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        mPagedArticles = mArticleViewModel.getArticleList();
        mPagedArticles.observe(this, articles -> {
            Log.d(TAG, "List<Result> onChanged");
            mArticles = articles;
            mAdapter = new ArticleAdapter(new DiffUtil.ItemCallback<Result>() {
                @Override
                public boolean areItemsTheSame(Result oldItem, Result newItem) {
                    Log.d(TAG, "areItemsTheSame");
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Result oldItem, Result newItem) {
                    Log.d(TAG, "areContentsTheSame");
                    return oldItem.getWebTitle().equals(newItem.getWebTitle());
                }
                
            });
            mAdapter.submitList(articles);
            mRecyclerView.setAdapter(mAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
            //TODO; Clear
            for (Result result : mArticles) {
                Log.d(TAG, result.getSectionName());
            }
        });

    }
}
