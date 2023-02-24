package com.asmejia93.articles.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asmejia93.articles.exception.NotFoundException;
import com.asmejia93.articles.model.Comment;
import com.asmejia93.articles.repository.CrudRepository;

@Service
public class CommentService implements CrudRepository<Comment> {
    public static List<Comment> comments = new ArrayList<>();

    public List<Comment> findAll() {
        return comments;
    }

    @Override
    public Comment findById(int id) {
        return comments.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comment not found"));
    }

    @Override
    public Comment save(Comment entity) {
        comments.add(entity);
        return entity;
    }

    @Override
    public Comment update(int id, Comment entity) {
        comments.removeIf((item) -> item.getId() == id);
        comments.add(entity);
        return entity;
    }

    @Override
    public void delete(int id) {
        comments.removeIf((item) -> item.getId() == id);
    }
}