package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Identifiable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseRepository<T extends Identifiable>{
    protected List<T> entities = new ArrayList<>();

    public T create(T entity) {
        entities.add(entity);
        return entities.getLast();
    }

    public T findById(Long id) {
        return entities
                .stream()
                .filter(entity -> entity.getId().equals(id))
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
