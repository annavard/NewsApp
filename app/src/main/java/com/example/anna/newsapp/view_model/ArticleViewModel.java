package com.example.anna.newsapp.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.model.repository.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    public static final String TAG = "ArticleViewModel";
    private LiveData<List<com.example.anna.newsapp.model.db.Photo>> photos;
    private ArticleRepository mArticleRepository;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ArticleViewModel public constructor");
        if (mArticleRepository == null) {
            mArticleRepository = new ArticleRepository(application);
        }
    }


    public LiveData<List<com.example.anna.newsapp.model.db.Photo>> getPhotos() {
        Log.d(TAG, "getPhotos");
        //TODO; add Dagger 2
        mArticleRepository.loadPhotos();
        photos = mArticleRepository.getPhotos();
        return photos;
    }

}
