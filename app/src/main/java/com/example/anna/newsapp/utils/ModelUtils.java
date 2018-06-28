package com.example.anna.newsapp.utils;

import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ModelUtils {

    public static List<Article> pojoToEntity(List<Result> resultList) {
        List<Article> articles = new ArrayList<>();
        for (Result result : resultList) {
            Article article = new Article();
            article.setSectionName(result.getSectionName());
            article.setWebTitle(result.getWebTitle());
            if (result.getFields() != null && result.getFields().getThumbnail() != null) {
                article.setThumbnail(result.getFields().getThumbnail());
            }
            articles.add(article);
        }
        return articles;
    }

    public static Article[] toArray(List<Article> articles) {
        Article[] articleArray = new Article[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            articleArray[i] = articles.get(i);
        }
        return articleArray;
    }
}
