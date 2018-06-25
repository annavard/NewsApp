package com.example.anna.newsapp.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {

    public static String TAG = "ArticleRoomDatabase";

    private static ArticleRoomDatabase INSTANCE;

    public static ArticleRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleRoomDatabase.class, "photo_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArticleDao articleDao();
}
