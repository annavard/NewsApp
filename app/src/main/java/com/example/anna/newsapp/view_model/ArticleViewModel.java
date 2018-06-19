package com.example.anna.newsapp.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.anna.newsapp.model.ArticleDataSource;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.repository.ArticleRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class ArticleViewModel extends ViewModel {

    public static final String TAG = "ArticleViewModel";
    public static final int  PAGE_SIZE = 20;
    public static final boolean ENABLE_PLACEHOLDERS = true;
    private LiveData<PagedList<Result>> articleList;

//    private ArticleRepository articleRepository;
    private DataSource<Long, Result> mDataSource;

    public void experiment(){
         mDataSource = new DataSource.Factory<Long, Result>() {
             @Override
             public DataSource<Long, Result> create() {
                 return null;
             }
         }.create();
    }


    public LiveData<PagedList<Result>> getArticleList() {
        Log.d(TAG, "getArticleList");
        //TODO; add Dagger 2

        PagedList.Config config =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(10)
                        .setPageSize(20)
                        .build();

        ArticleRepository repo = new ArticleRepository();
        ArticleDataSource articleDataSource = new ArticleDataSource(repo);

        articleList = new LivePagedListBuilder(mDataSource, config).build();

//        articleList = repo.getArticles();
        return articleList;
    }
}
