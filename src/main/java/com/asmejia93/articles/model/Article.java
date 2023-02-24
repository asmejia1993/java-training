package com.asmejia93.articles.model;

import java.util.ArrayList;

import com.asmejia93.articles.constants.ArticleType;

import jakarta.validation.constraints.NotBlank;

public class Article implements Comparable<Article> {
    private int id;
    @NotBlank(message = "Cannot be null/empty")
    private String title;
    @NotBlank(message = "Cannot be null/empty")
    private String body;
    private ArticleType type;

    public Article() {
    }

    public Article(int id, String title, String body, ArticleType type) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
    }

    public Article(int i, String randomAlphabetic, String randomAlphabetic2, ArticleType literatureReview,
            ArrayList<Object> newArrayList) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Article arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

}