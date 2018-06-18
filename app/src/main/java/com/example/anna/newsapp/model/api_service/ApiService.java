package com.example.anna.newsapp.model.api_service;


import android.util.Log;

public class ApiService {
    public static String TAG = "ApiService";
    public static String BASE_URL = "https://content.guardianapis.com/";

    public static ApiInterface getService() {
        Log.d(TAG, "getService");
        return ApiClient.getInstance().create(ApiInterface.class);
    }
}
