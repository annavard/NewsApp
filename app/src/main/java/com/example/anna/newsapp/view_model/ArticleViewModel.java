package com.example.anna.newsapp.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.repository.ArticleRepository;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    public static final String TAG = "ArticleViewModel";
    private LiveData<List<Article>> articles;
    private ArticleRepository articleRepository;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ArticleViewModel");
        articleRepository = ArticleRepository.getInstance(application);
    }


    public LiveData<List<Article>> getArticles(int pageNumber, int pageSize) {
        Log.d(TAG, "getArticleList");
        Log.d(MainActivity.TAG, "ArticleViewModel - getArticleList - pageNumber: " + pageNumber);

        articles = articleRepository.loadFromDB(pageNumber, pageSize);
        return articles;
    }

    public void savePinnedItem(Article pinned) {
        articleRepository.savePinned(pinned);
    }
}
