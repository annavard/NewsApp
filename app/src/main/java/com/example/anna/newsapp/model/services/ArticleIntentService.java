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

        MainActivity.PAGE_NUMBER++;
        LiveData<List<Article>> liveArticles = ArticleRepository.getInstance(getApplication())
                .loadFromNetwork(MainActivity.PAGE_NUMBER, MainActivity.PAGE_SIZE);

        liveArticles.observeForever(articles -> {
            Log.d(MainActivity.TAG, "onStartJob - onChanged!!!!!!");
//                liveArticles.removeObserver(this);
            NotificationUtils.showNotification(context, articles.get(0).getSectionName(), articles.get(0).getWebTitle());

        });

    }
}
