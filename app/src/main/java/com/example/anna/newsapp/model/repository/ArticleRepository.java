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
    private MutableLiveData<List<Photo>> mutableLiveData;


    public MutableLiveData<List<Photo>> getPhotos() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        Log.d(TAG, "getResults");
        ApiService.getService().getPhotos().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + "\n" + t.getStackTrace());
            }
        });
        return mutableLiveData;
    }
}
