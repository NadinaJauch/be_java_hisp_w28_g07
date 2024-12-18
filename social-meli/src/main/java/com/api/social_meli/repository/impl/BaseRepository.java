package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseRepository<T extends Identifiable> {

    protected List<T> entities = new ArrayList<>();

    public T create(T entity) {
        entities.add(entity);
        return entities.get(entities.size()-1);
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

    public boolean exists(int id) {
        return entities.stream().anyMatch(x -> x.getId() == id);
    }

    protected void loadDataBase(String fileName) {
        try {
            File file;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // para que tome los DateTime

            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // una reflexion para obtener el tipo generico de esta clase base
            file = ResourceUtils.getFile("classpath:" + fileName + ".json");
            this.entities = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
