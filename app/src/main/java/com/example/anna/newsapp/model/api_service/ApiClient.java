package com.example.anna.newsapp.model.api_service;

import android.util.Log;

import com.example.anna.newsapp.model.models.another.Photo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

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

//            Gson gson = new Gson();
//
//
//// Deserialization
//            Type collectionType = new TypeToken<Collection<Photo>>(){}.getType();
//            Collection<Photo> photos = gson.fromJson("[  \n" +
//                    "   {  \n" +
//                    "      \"albumId\":1,\n" +
//                    "      \"id\":1,\n" +
//                    "      \"title\":\"accusamus beatae ad facilis cum similique qui sunt\",\n" +
//                    "      \"url\":\"http://placehold.it/600/92c952\",\n" +
//                    "      \"thumbnailUrl\":\"http://placehold.it/150/92c952\"\n" +
//                    "   },\n" +
//                    "   {  \n" +
//                    "      \"albumId\":1,\n" +
//                    "      \"id\":2,\n" +
//                    "      \"title\":\"reprehenderit est deserunt velit ipsam\",\n" +
//                    "      \"url\":\"http://placehold.it/600/771796\",\n" +
//                    "      \"thumbnailUrl\":\"http://placehold.it/150/771796\"\n" +
//                    "   },\n" +
//                    "\n" +
//                    "]", collectionType);


            return new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }


}
