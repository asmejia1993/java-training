package com.asmejia93.articles.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asmejia93.articles.exception.NotFoundException;
import com.asmejia93.articles.model.Article;
import com.asmejia93.articles.repository.CrudRepository;

@Service
public class ArticleService implements CrudRepository<Article> {

    public static List<Article> articles = new ArrayList<>();
    // (Arrays.asList(
    // new Article(1, "dsfsdfsdfsdf",
    // randomAlphabetic(200), CASE_STUDY, newArrayList()),
    // new Article(2, randomAlphabetic(10),
    // randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList()),
    // new Article(3, randomAlphabetic(10),
    // randomAlphabetic(200), LITERATURE_REVIEW, newArrayList())));

    public List<Article> findAll() {
        return articles;
    }

    public Article findById(int id) {
        return articles.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Article not found"));
    }

    public Article save(Article entity) {
        articles.add(entity);
        return entity;
    }

    public Article update(int id, Article entity) {
        articles.removeIf((item) -> item.getId() == id);
        articles.add(entity);
        return entity;
    }

    public void delete(int id) {
        articles.removeIf((item) -> item.getId() == id);
    }
}