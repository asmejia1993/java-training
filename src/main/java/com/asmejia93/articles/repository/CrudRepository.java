package com.asmejia93.articles.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepository<T> {
    public List<T> findAll();

    public T findById(int id);

    public T save(T entity);

    public T update(int id, T entity);

    public void delete(int id);
}
