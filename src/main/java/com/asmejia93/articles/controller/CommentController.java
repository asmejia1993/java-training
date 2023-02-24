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

import com.asmejia93.articles.model.Comment;
import com.asmejia93.articles.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "comments")
public class CommentController {

  private CommentService service;

  public CommentController(CommentService service) {
    this.service = service;
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
  public @ResponseBody Comment saveComment(@Valid @RequestBody Comment request) {
    return service.save(request);
  }

  @PutMapping(value = "/{id}")
  public @ResponseBody Comment updateComment(@PathVariable Integer id, @RequestBody Comment request) {
    return service.update(id, request);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteComment(@PathVariable Integer id) {
    service.delete(id);
  }
}