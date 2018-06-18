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

public class ArticleViewModel extends ViewModel {
    public static final String TAG = "ArticleViewModel";
    private LiveData<PagedList<Result>> articleList;
    private ArticleRepository articleRepository;
    private DataSource.Factory<Long, Result> mDataSource;

    public ArticleViewModel(DataSource.Factory<Long, Result> dataSource){
        mDataSource = dataSource;
    }

    public LiveData<PagedList<Result>> getArticleList() {
        Log.d(TAG, "getArticleList");
        //TODO; add Dagger 2


        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(10)
                        .setPageSize(20).build();

        articleList = (new LivePagedListBuilder(mDataSource.usersByFirstName(), pagedListConfig))
                .build();

        articleRepository = new ArticleRepository();
        articleList = articleRepository.getArticles();
        return articleList;
    }
}
