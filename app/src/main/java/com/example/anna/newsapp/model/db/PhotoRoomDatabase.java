package com.example.anna.newsapp.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anna.newsapp.model.repository.ArticleRepository;


@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoRoomDatabase extends RoomDatabase {

    public static String TAG = "PhotoRoomDatabase";

    private static PhotoRoomDatabase INSTANCE;

    public static PhotoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhotoRoomDatabase.class, "photo_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PhotoDao articleDao();


    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "RoomDatabase.Callback onCreate!!!!!!!!!!!!!");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "RoomDatabase.Callback onOpen!!!!!!!!!!!!!!!!!!");
        }
    };
}
