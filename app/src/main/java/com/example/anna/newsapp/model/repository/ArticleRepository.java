package com.example.anna.newsapp.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.models.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    public static final String TAG = "ArticleRepository";
    public static final int  PAGE_SIZE = 20;
    private MutableLiveData<List<Result>> articles;

    public LiveData<List<Result>> getArticles(int page) {
        Log.d(TAG, "getResults");
        if (articles == null) {
            articles = new MutableLiveData<>();
        }
        ApiService.getService().getArticles("test", "thumbnail", page, PAGE_SIZE).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                    articles.setValue(response.body().getResponse().getResults());
                    //TODO; CLEAR
                    Log.d(TAG, "currentPage!!!!!!!!!!!!!! " + response.body().getResponse().getCurrentPage().toString());
                    Log.d(TAG, "PageSize!!!!!!!!!!!!! " + response.body().getResponse().getPageSize().toString());
                    for (Result result : articles.getValue()) {
                        Log.d(TAG, result.getSectionName());
                        //TODO; clear
//                        Log.d(TAG, "getSectionName: " + result.getSectionName());
//                        Log.d(TAG, "getThumbnail: " + result.getFields().getThumbnail());
                    }
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getStackTrace());
            }
        });
        return articles;
    }
}
