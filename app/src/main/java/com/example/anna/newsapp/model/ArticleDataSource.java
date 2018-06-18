package com.example.anna.newsapp.model;

import android.arch.paging.DataSource;
import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.models.Result;

import java.util.List;

class ArticleDataSource extends PositionalDataSource<Result> {

    private int computeCount() {
        // actual count code here
        return 10;
    }

    private List<Result> loadRangeInternal(int startPosition, int loadCount) {
        // actual load code here
       return ApiService.getService().getArticles("test", "thumbnail", startPosition, loadCount);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<Result> callback) {
        int totalCount = computeCount();
        int position = computeInitialLoadPosition(params, totalCount);
        int loadSize = computeInitialLoadSize(params, position, totalCount);
        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<Result> callback) {
        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize));
    }
}