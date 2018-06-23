package com.example.anna.newsapp.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.db.PhotoDao;
import com.example.anna.newsapp.model.db.PhotoRoomDatabase;
import com.example.anna.newsapp.model.models.another.Photo;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    public static final String TAG = "ArticleRepository";
    //    private MutableLiveData<List<com.example.anna.newsapp.model.db.Photo>> mutableLiveData;
//    private LiveData<List<com.example.anna.newsapp.model.db.Photo>> mLivePhotos;
    private PhotoDao mArticleDao;

    public ArticleRepository(Application application) {
        PhotoRoomDatabase db = PhotoRoomDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        loadPhotos();
    }


    public void populateDb(List<com.example.anna.newsapp.model.db.Photo> photos) {
        com.example.anna.newsapp.model.db.Photo[] photArray = new com.example.anna.newsapp.model.db.Photo[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            photArray[i] = photos.get(i);
        }
        new insertAsyncTask(mArticleDao).execute(photArray);
    }

    public LiveData<List<com.example.anna.newsapp.model.db.Photo>> getPhotos() {
        return mArticleDao.getAllPhotos();
    }


    public void loadPhotos() {
        Log.d(TAG, "loadPhotos");
//        if (mutableLiveData == null) {
//            mutableLiveData = new MutableLiveData<>();
//        }

        ApiService.getService().getPhotos().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
//                    mutableLiveData.setValue(response.body());
                    List<com.example.anna.newsapp.model.db.Photo> miniList = shrinkList(pojoToEntity(response.body()));
                    Log.d(TAG, "page number: " + MainActivity.mPageNumber);
                    Log.d(TAG, "list size: " + miniList.size());
                    for(com.example.anna.newsapp.model.db.Photo photo : miniList){
                        Log.d(TAG, photo.getImageURL());
                    }
                    populateDb(miniList);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + "\n" + t.getStackTrace());
            }
        });
    }


    public List<com.example.anna.newsapp.model.db.Photo> shrinkList(List<com.example.anna.newsapp.model.db.Photo> list) {
        return list.subList(MainActivity.mPageNumber, MainActivity.mPageNumber + 10);
    }

    public List<com.example.anna.newsapp.model.db.Photo> pojoToEntity(List<Photo> list) {
        List<com.example.anna.newsapp.model.db.Photo> photos = new ArrayList<>();
        for (Photo photo : list) {
            com.example.anna.newsapp.model.db.Photo minimizedPhoto = new com.example.anna.newsapp.model.db.Photo();
            minimizedPhoto.setAlbumId(photo.getAlbumId().toString());
            minimizedPhoto.setTitle(photo.getTitle());
            minimizedPhoto.setImageURL(photo.getThumbnailUrl());
            photos.add(minimizedPhoto);
        }
        return photos;
    }


    private static class insertAsyncTask extends AsyncTask<com.example.anna.newsapp.model.db.Photo, Void, Void> {

        private PhotoDao mAsyncTaskDao;

        insertAsyncTask(PhotoDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(com.example.anna.newsapp.model.db.Photo... photos) {
            mAsyncTaskDao.insertAll(photos);
            return null;
        }
    }

}

