package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Post;
import com.api.social_meli.model.Product;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IPostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import java.util.List;

@Repository
public class PostRepositoryImpl extends BaseRepository<Post> implements IPostRepository {

    public PostRepositoryImpl() throws IOException {
        loadDataBase();
    }


    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts ;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts= objectMapper.readValue(file,new TypeReference<List<Post>>(){});
        this.entities = posts;
    }

    @Override
    public List<Post> findByUserId(int userId) {
        return entities.stream()
                .filter(x -> x.getUser().getUserId() == userId)
                .toList();
    }



}
