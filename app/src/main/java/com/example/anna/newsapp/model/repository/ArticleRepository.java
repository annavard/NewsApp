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

    public List<Result> getArticles(int startPosition, int loadSize) {
//        final MutableLiveData<List<Result>> result = new MutableLiveData<>();
        final List<Result>[] result = new List[]{new ArrayList<>()};
        Log.d(TAG, "getResults");
        ApiService.getService().getArticles("test", "thumbnail", startPosition, loadSize)
                .enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
//                    result.setValue(response.body().getResponse().getResults());
                    result[0] = response.body().getResponse().getResults();
                    //TODO; CLEAR
                    for (Result result : result[0]) {
                        Log.d(TAG, result.getSectionName());
                    }

                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
        return result[0];
    }
}
