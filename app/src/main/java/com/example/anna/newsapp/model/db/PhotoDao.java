package com.example.anna.newsapp.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;



import java.util.List;

@Dao
public interface PhotoDao {

    @Insert
    void insertAll(Photo... photos);

    @Query("DELETE FROM photo_database")
    void deleteAll();

    @Query("SELECT * FROM photo_database")
    LiveData<List<Photo>> getAllPhotos();
}
