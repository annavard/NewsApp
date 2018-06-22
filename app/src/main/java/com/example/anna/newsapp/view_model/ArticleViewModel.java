package com.example.anna.newsapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.repository.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends ViewModel implements ArticleRepository.DataFetchListener {
    public static final String TAG = "ArticleViewModel";
    private LiveData<List<Result>> articleList;
    private ArticleRepository articleRepository;


    public LiveData<List<Result>> getArticleList() {
        Log.d(TAG, "getArticleList");
        //TODO; add Dagger 2
        return articleList;
    }

    public void init() {
        Log.d(TAG, "getArticleList");
        if (articleList != null) {
            return;
        }
        if (articleRepository == null) {
            articleRepository = new ArticleRepository(this);
        }
        articleList = articleRepository.getArticles();
    }

    @Override
    public void onReceeived(LiveData<List<Result>> resultList) {
        articleList = resultList;

    }
}
