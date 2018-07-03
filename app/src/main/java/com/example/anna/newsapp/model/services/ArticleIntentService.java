package com.example.anna.newsapp.model.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.repository.ArticleRepository;
import com.example.anna.newsapp.utils.NotificationUtils;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.List;

public class ArticleIntentService extends IntentService {

    public static int PAGE_NUMBER = 1;
    public static int PAGE_SIZE = 5;

    private Context context = this;

    public ArticleIntentService() {
        super("ArticleIntentService");

    }

    public ArticleIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.TAG, "ArticleIntentService - onCreate");
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(MainActivity.TAG, "ArticleIntentService - onHandleIntent");

       ArticleRepository.getInstance(getApplication()).getmArticle()
                .observeForever(new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                Log.d(MainActivity.TAG, "onHandleIntent - onChanged!!!!!!");
                NotificationUtils.showNotification(context, article.getSectionName(), article.getWebTitle());
            }
        });


        ArticleRepository.getInstance(getApplication()).loadFromNetwork(PAGE_NUMBER, PAGE_SIZE, true);

    }
}
