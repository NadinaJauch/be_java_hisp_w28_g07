package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Post;
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

    protected void loadDataBase() {
        try {
            File file;
            ObjectMapper objectMapper = new ObjectMapper();
            List<Post> objects;
            file = ResourceUtils.getFile("classpath:posts.json");
            objects = objectMapper.readValue(file,new TypeReference<List<Post>>(){});
            this.entities = objects;
        }
        catch(IOException ignored) {
        }
    }
}
