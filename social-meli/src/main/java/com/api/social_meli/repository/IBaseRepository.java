package com.api.social_meli.repository;

import java.util.List;

public interface IBaseRepository<T> {
    T create(T entity);
    T findById(Long id);
    List<T> findAll();
    void delete(T entity);
}