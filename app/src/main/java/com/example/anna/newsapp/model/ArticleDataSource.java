package com.example.anna.newsapp.model;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.repository.ArticleRepository;

import java.util.List;

public class ArticleDataSource extends PositionalDataSource<Result> {
    public static String TAG = "PositionalDataSource";
    private ArticleRepository articleRepository;

    public ArticleDataSource(){
        this.articleRepository = articleRepository;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Result> callback) {
        Log.d(TAG, "loadInitial");
        List<Result> result= articleRepository.getArticles(params.requestedStartPosition, params.requestedLoadSize);
        callback.onResult(result, 0);

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Result> callback) {
        Log.d(TAG, "loadRange");
        ArticleRepository repository = new ArticleRepository();
        List<Result> result= repository.getArticles(params.startPosition, params.loadSize);
        callback.onResult(result);

    }
}