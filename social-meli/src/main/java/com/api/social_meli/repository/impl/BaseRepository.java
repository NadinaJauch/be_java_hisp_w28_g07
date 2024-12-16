package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Identifiable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseRepository<T extends Identifiable>{
    protected List<T> entities = new ArrayList<>();

    public T create(T entity) {
        entities.add(entity);
        return entities.get(entities.size()-1); //Nota a pull review: Reemplazar .getLast por esto
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
