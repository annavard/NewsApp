package com.example.anna.newsapp.model;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ArticleDataHolder {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public ArticleDataHolder() {
        articles = new ArrayList<>();
    }

    private static final ArticleDataHolder articleDataHolder = new ArticleDataHolder();

    public static ArticleDataHolder getInstance() {
        return articleDataHolder;
    }


}
