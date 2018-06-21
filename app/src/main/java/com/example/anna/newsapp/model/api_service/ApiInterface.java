package com.example.anna.newsapp.model.api_service;


import com.example.anna.newsapp.model.models.Example;
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.model.models.another.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/search")
    Call<Example> getArticles(@Query("api-key") String apiKey,
                              @Query("show-fields") String showFields,
                              @Query("page") int page,
                              @Query("page-size") int pageSize);

    @GET("/search")
    Call<Example> getArticles(@Query("api-key") String apiKey,
                              @Query("show-fields") String showFields);


    @GET("/photos")
    Call<List<Photo>> getPhotos();
}
