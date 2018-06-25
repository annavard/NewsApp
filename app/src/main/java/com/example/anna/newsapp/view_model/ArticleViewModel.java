package com.example.anna.newsapp.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.repository.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    public static final String TAG = "ArticleViewModel";
    private LiveData<List<Article>> articleList;
    private ArticleRepository articleRepository;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ArticleViewModel");
        if (articleRepository == null) {
            articleRepository = new ArticleRepository(application);
        }
    }


    public LiveData<List<Article>> getArticleList(int pageNumber) {
        Log.d(TAG, "getArticleList");

        articleList = articleRepository.getArticles();
        for(Article article : articleList.getValue()){
            Log.d(TAG, article.getWebTitle());
        }
        return articleList;
    }

}
