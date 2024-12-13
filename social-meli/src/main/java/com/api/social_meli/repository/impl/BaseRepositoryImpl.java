package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Identifiable;
import com.api.social_meli.repository.IBaseRepository;

import java.util.ArrayList;
import java.util.List;

public class BaseRepositoryImpl<T extends Identifiable> implements IBaseRepository<T> {
    private List<T> entities = new ArrayList<>();
    @Override
    public T create(T entity) {
        entities.add(entity);
        return entities.getLast();
    }

    @Override
    public T findById(Long id) {
        return entities
                .stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> findAll() {
        return entities;
    }

    @Override
    public void delete(T entity) {
        entities.remove(entity);
    }
}
