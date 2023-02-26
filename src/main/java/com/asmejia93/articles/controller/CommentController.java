package com.asmejia93.articles.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.asmejia93.articles.dto.CommentDto;
import com.asmejia93.articles.model.Article;
import com.asmejia93.articles.model.Comment;
import com.asmejia93.articles.service.ArticleService;
import com.asmejia93.articles.service.CommentService;

@RestController
@RequestMapping(value = "comments")
public class CommentController {

  private CommentService service;
  private ArticleService articleService;

  public CommentController(CommentService service, ArticleService articleService) {
    this.service = service;
    this.articleService = articleService;
  }

  @GetMapping
  public @ResponseBody List<Comment> findAllComments() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}")
  public @ResponseBody Comment findCommentById(@PathVariable Integer id) {
    return service.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody Comment saveComment(@Valid @RequestBody CommentDto request) {
    Article article = articleService.findById(request.getArticleId());
    Comment comment = new Comment(request.getId(), article, request.getEmail(), request.getText());
    return service.save(comment);
  }

  @PutMapping(value = "/{id}")
  public @ResponseBody Comment updateComment(@PathVariable Integer id, @Valid @RequestBody CommentDto request) {
    Article article = articleService.findById(request.getArticleId());
    Comment comment = new Comment(request.getId(), article, request.getEmail(), request.getText());
    return service.update(id, comment);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteComment(@PathVariable Integer id) {
    service.delete(id);
  }
}