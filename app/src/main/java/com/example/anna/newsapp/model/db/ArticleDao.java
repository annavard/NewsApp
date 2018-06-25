package com.example.anna.newsapp.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insertAll(Article... articles);

    @Query("DELETE FROM articles")
    void deleteAll();

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();


}
