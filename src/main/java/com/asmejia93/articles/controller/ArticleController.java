package com.asmejia93.articles.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asmejia93.articles.model.Article;
import com.asmejia93.articles.service.ArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody List<Article> findAllArticles() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody Article findArticleById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Article saveArticle(@Valid @RequestBody Article request) {
        return service.save(request);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody Article updateArticle(@PathVariable Integer id, @RequestBody Article request) {
        return service.update(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        service.delete(id);
    }
}