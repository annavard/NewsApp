package com.example.anna.newsapp.model;

import com.example.anna.newsapp.model.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ArticleDataHolder {
    private List<Result> articles;

    public List<Result> getArticles() {
        return articles;
    }

    public void setArticles(List<Result> articles) {
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
