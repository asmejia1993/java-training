package com.asmejia93.articles.model;

public class Comment implements Comparable<Comment> {
    private int id;
    private String email;
    private String text;
    private Article article;

    public Comment() {
    }

    public Comment(int id, Article article, String email, String text) {
        this.id = id;
        this.article = article;
        this.email = email;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticleId(Article article) {
        this.article = article;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Comment arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
