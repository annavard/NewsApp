package com.example.anna.newsapp.model.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.repository.ArticleRepository;
import com.example.anna.newsapp.utils.NotificationUtils;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.List;

public class ArticleJobService extends JobService {

    public static String TAG = "ArticleJobService";

    private Context context = this;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob");
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob");
        return false;
    }
}
