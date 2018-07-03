package com.example.anna.newsapp.model.repository;

import android.app.Application;
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
import com.example.anna.newsapp.model.services.ArticleIntentService;
import com.example.anna.newsapp.utils.ModelUtils;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.anna.newsapp.view.activities.MainActivity.PAGE_SIZE;

public class ArticleRepository {

    public static final String TAG = "ArticleRepository";

    private ArticleDao mArticleDao;

    private MutableLiveData<List<Article>> mArticles;
    private MutableLiveData<Article> mArticle;

    public static ArticleRepository INSTANCE;

    public static synchronized ArticleRepository getInstance(Application application) {
        if (INSTANCE == null) {
            Log.d(TAG, "ArticleRepository getInstance is NULL");
            INSTANCE = new ArticleRepository(application);
        }

        return INSTANCE;
    }

    private ArticleRepository(Application application) {
        Log.d(TAG, "ArticleRepository constructor");
        mArticles = new MutableLiveData<>();
        mArticle = new MutableLiveData<>();
        ArticleRoomDatabase db = ArticleRoomDatabase.getInstance(application);
        mArticleDao = db.articleDao();
    }


    public void loadFromNetwork(int pageNumber, int pageSize, boolean isFromService) {
        Log.d(TAG, "loadFromNetwork ArticleIntentService.PAGE_NUMBER - " + ArticleIntentService.PAGE_NUMBER);
        ApiService.getService().getArticles("test", "thumbnail", pageNumber, pageSize).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                    ArticleIntentService.PAGE_NUMBER++;
                    List<Article> articles = ModelUtils.pojoToEntity(response.body().getResponse().getResults());
                    populateDb(articles);
                    if (isFromService) {
                        Log.d(TAG, "loadFromNetwork - isFromService: !!!!!!!!" + isFromService);
                        mArticle.postValue(articles.get(0));
                    } else {
                        mArticles.setValue(articles);
                    }
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getStackTrace());
            }
        });
    }


    public MutableLiveData<Article> getmArticle() {
        return mArticle;
    }

    public void populateDb(List<Article> articles) {
        Log.d(TAG, "populateDb");
        int itemCount = getItemCount();
        Log.d(TAG, "itemCount: " + itemCount);
        if (getItemCount() >= PAGE_SIZE * 10) {
            new Thread(() -> mArticleDao.deleteAll()).start();
            MainActivity.PAGE_NUMBER = 1;
            ArticleIntentService.PAGE_NUMBER = 1;
        }
        new insertAsyncTask(mArticleDao).execute(ModelUtils.toArray(articles));
    }


    public MutableLiveData<List<Article>> loadFromDB(int pageNumber, int pageSize) {
        Log.d(TAG, "loadFromDB MainActivity.PAGE_NUMBER - " + pageNumber);
        int itemCount = getItemCount();
        Log.d(TAG, "loadFromDB - itemCount - " + itemCount);
        if (itemCount <= pageNumber * pageSize) {
            MainActivity.PAGE_NUMBER++;
            loadFromNetwork(pageNumber, pageSize, false);
            return mArticles;
        }
        new Thread(() -> {
            List<Article> articles = mArticleDao.getArticles(pageNumber, pageSize);
            Log.d(TAG, "articles size - " + articles.size());
            mArticles.postValue(articles);
            MainActivity.PAGE_NUMBER++;
        }).start();

        return mArticles;
    }


    private int getItemCount() {
        final int[] itemCount = new int[1];
        new Thread(() -> itemCount[0] = mArticleDao.getAllArticles().size()).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemCount[0];
    }


    public void savePinned(Article article) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mArticleDao.savePinned(article);
            }
        }).start();
    }


    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao articleDao;

        public insertAsyncTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            articleDao.insertAll(articles);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


}
