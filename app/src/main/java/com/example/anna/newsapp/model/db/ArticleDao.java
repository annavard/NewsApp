package com.example.anna.newsapp.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArticleDao {

//    @Insert(onConflict = REPLACE)
//    void insertAll(Article... articles);

    @Insert(onConflict = REPLACE)
    void insertAll(Article... articles);


    @Query("DELETE FROM articles")
    void deleteAll();

    @Query("SELECT * FROM articles")
    List<Article> getAllArticles();




}
