package com.example.anna.newsapp.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.anna.newsapp.model.services.ArticleJobService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class JobUtils {

    public static String TAG = "JobUtils";

    public static void scheduleJob(Context context){
        ComponentName componentName = new ComponentName(context, ArticleJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(3000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled!");
        } else {
            Log.d(TAG, "Job not scheduled");
        }

        jobScheduler.cancelAll();
    }
}
