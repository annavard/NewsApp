package com.example.anna.newsapp.model.repository;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.anna.newsapp.model.DummyData;
import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.db.ArticleDao;
import com.example.anna.newsapp.model.db.ArticleRoomDatabase;
import com.example.anna.newsapp.model.models.Example;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.utils.ModelUtils;
import com.example.anna.newsapp.utils.NetworkUtils;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    public static final String TAG = "ArticleRepository";
    public static final int PAGE_SIZE = 5;
    public static final int FIRST_PAGE = 1;

    private Context mContext;

    private ArticleDao mArticleDao;

    private MutableLiveData<List<Article>> mArticles;

    public ArticleRepository(Application application) {
        Log.d(TAG, "ArticleRepository constructor");
        mContext = application;
        mArticles = new MutableLiveData<>();
        ArticleRoomDatabase db = ArticleRoomDatabase.getInstance(application);
        mArticleDao = db.articleDao();
    }

    public LiveData<List<Article>> getArticles(int page) {
        Log.d(TAG, "getArticles");
        if (NetworkUtils.isOnline(mContext)) {
            Log.d(TAG, "isOnline");
            mArticles = loadFromNetwork(page);
        } else {
            Log.d(TAG, "is NOT Online");
            mArticles = loadFromDB(page);
        }
//        List<Article> articles = DummyData.initData();
//        mArticles.setValue(articles);
//        populateDb(articles);
        return mArticles;
    }

    public MutableLiveData<List<Article>> loadFromNetwork(int pageNumber) {
        Log.d(TAG, "loadFromNetwork - pageNumber: " + pageNumber);
        ApiService.getService().getArticles("test", "thumbnail", pageNumber, PAGE_SIZE).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                    List<Article> articles = ModelUtils.pojoToEntity(response.body().getResponse().getResults());
                    mArticles.setValue(articles);
                    populateDb(articles);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getStackTrace());
            }
        });
        return mArticles;
    }


    public MutableLiveData<List<Article>> loadFromDB(int page) {
        Log.d(TAG, "loadFromDB");
        Log.d(TAG, "page - " + page);
        if(page > 1) {
            return  null;
//            mArticles.removeObservers(MainActivity.this);
        }
        new Thread(() -> {
            List<Article> articles = mArticleDao.getAllArticles();
            Log.d(TAG, "articles size - " + articles.size());
            mArticles.postValue(articles);
        }).start();

        return mArticles;
    }

    private int getItemCount(){
        final int[] itemCount = new int[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                itemCount[0] = mArticleDao.getAllArticles().size();
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemCount[0];
    }


    public void populateDb(List<Article> articles) {
        Log.d(TAG, "populateDb");
        int itemCount = getItemCount();
        Log.d(TAG, "itemCount: " + itemCount);
        if (getItemCount() >= PAGE_SIZE * 2) {
            Log.d(TAG, "getItemCount() > PAGE_SIZE * 2");
            new Thread(() -> mArticleDao.deleteAll()).start();
            int afterItemCount = getItemCount();
            Log.d(TAG, "afterItemCount - " + afterItemCount);
        }
        new insertAsyncTask(mArticleDao).execute(ModelUtils.toArray(articles));
    }


    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao articleDao;

        public insertAsyncTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            Log.d(TAG, "doInBackground");
            articleDao.insertAll(articles);
            return null;
        }
    }


}
