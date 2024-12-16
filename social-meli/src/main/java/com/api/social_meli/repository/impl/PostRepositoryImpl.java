package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Post;
import com.api.social_meli.model.Product;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IPostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class PostRepositoryImpl extends BaseRepository<Post> implements IPostRepository {

    public PostRepositoryImpl() throws IOException {
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Registra el módulo para manejar fechas Java 8
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Post> posts ;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts= objectMapper.readValue(file,new TypeReference<List<Post>>(){});
        this.entities = posts;
    }

    @Override
    public List<Post> findByUserId(int userId) {
        return entities.stream()
                .filter(x -> x.getSeller().getUserId() == userId)
                .toList();
    }
}
