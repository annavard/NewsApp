package com.example.anna.newsapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.view.activities.MainActivity;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    private static final String CHANNEL_ID = "article_channel";
    private static final int REQUEST_CODE = 0;

    public static void showNotification(Context context, String section, String title) {
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, REQUEST_CODE, new Intent(context, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(createNotificationChannel(context));
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(section)
                .setContentText(title)
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setLights(Color.GREEN, 1000, 500);;

        manager.notify(generateId(), builder.build());

    }

    private static int generateId(){
        return (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel(Context context) {

        CharSequence name = context.getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        channel.setVibrationPattern(new long[]{0, 400, 100, 400});
        channel.enableVibration(true);
        return channel;


    }
}
