package com.example.anna.newsapp.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.models.Example;
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.view_model.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    public static final String TAG = "ArticleRepository";
    private ArticleViewModel viewModel;

    public ArticleRepository(ArticleViewModel viewModel){
        this.viewModel = viewModel;
    }

//    public MutableLiveData<List<Result>> getArticles(int startPosition) {
//        final MutableLiveData<List<Result>> result = new MutableLiveData<>();
//        Log.d(TAG, "getResults");
//        ApiService.getService().getArticles("test", "thumbnail").enqueue(new Callback<Example>() {
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//                Log.d(TAG, "onResponse");
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "isSuccessful");
//                    result.setValue(response.body().getResponse().getResults());
//                    //TODO; CLEAR
////                    for (Result result : articles[0]) {
////                        Log.d(TAG, result.getSectionName());
////                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//                Log.d(TAG, "onFailure");
//            }
//        });
//        return result;
//    }

    public MutableLiveData<List<Photo>> getPhotos() {
        final MutableLiveData<List<Photo>> result = new MutableLiveData<>();
        Log.d(TAG, "getResults");
        ApiService.getService().getPhotos().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                    viewModel.setPhotos(response.body());
                    result.postValue(response.body());
                    //TODO; CLEAR
                    for (Photo result : response.body()) {
                        Log.d(TAG, result.getThumbnailUrl());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + "\n" +  t.getStackTrace());
            }
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return result;
    }
}
