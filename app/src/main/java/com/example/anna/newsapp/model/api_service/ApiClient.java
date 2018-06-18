package com.example.anna.newsapp.model.api_service;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String TAG = "ApiClient";
    private static Retrofit INSTANCE;

    private ApiClient(){

    }

    public static Retrofit getInstance(){
        Log.d(TAG, "getInstance");
        if(INSTANCE == null){

            LoggingInterceptor interceptor = new LoggingInterceptor();

//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            return new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }


}
