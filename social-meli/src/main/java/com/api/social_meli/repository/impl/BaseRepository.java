package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class BaseRepository<T extends Identifiable>{
    private List<T> entities = new ArrayList<>();

    public T create(T entity) {
        entities.add(entity);
        return entities.getLast();
    }

    public T findById(int id) {
        return entities
                .stream()
                .filter(entity -> entity.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<T> findAll() {
        return entities;
    }

    public void delete(T entity) {
        entities.remove(entity);
    }
}
