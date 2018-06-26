package com.example.anna.newsapp.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.anna.newsapp.model.DummyData;
import com.example.anna.newsapp.model.api_service.ApiService;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.db.ArticleDao;
import com.example.anna.newsapp.model.db.ArticleRoomDatabase;
import com.example.anna.newsapp.model.models.Result;
import com.example.anna.newsapp.model.models.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    public static final String TAG = "ArticleRepository";
    public static final int PAGE_SIZE = 20;

    private ArticleDao mArticleDao;

    private MutableLiveData<List<Article>> articles;

    public ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getInstance(application);
        mArticleDao = db.articleDao();
        loadArticles(1);
    }

    public void loadArticles(int page) {
        Log.d(TAG, "getArticles");
//      if(articles == null){
//          articles = new MutableLiveData<>();
//      }
//        ApiService.getService().getArticles("test", "thumbnail", page, PAGE_SIZE).enqueue(new Callback<Example>() {
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//                Log.d(TAG, "onResponse");
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "isSuccessful");
//                    List<Article> articles = pojoToEntity(response.body().getResponse().getResults());
//                    populateDb(articles);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getStackTrace());
//            }
//        });

        populateDb(DummyData.populateData());
    }

    public MutableLiveData<List<Article>> getArticles() {
              if(articles == null){
          articles = new MutableLiveData<>();
      }
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Article> result = mArticleDao.getAllArticles();
                articles.postValue(result);
//                for(Article article : articles){
//                    Log.d(TAG, article.getWebTitle());
//                }

                for(Article article : result){
                    Log.d(TAG, article.getWebTitle());
                }
            }
        }).start();

        return articles;
    }

    public void populateDb(List<Article> articles) {
        Log.d(TAG, "populateDb");
        Article[] articleArray = new Article[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            articleArray[i] = articles.get(i);
        }

        new insertAsyncTask(mArticleDao).execute(articleArray);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mArticleDao.insertAll(articles);
//            }
//        }).start();
    }


    public List<Article> pojoToEntity(List<Result> resultList) {
        List<Article> articles = new ArrayList<>();
        for (Result result : resultList) {
            Article article = new Article();
            article.setSectionName(result.getSectionName());
            article.setWebTitle(result.getWebTitle());
            article.setThumbnail(result.getFields().getThumbnail());
            articles.add(article);
        }
        return articles;
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
