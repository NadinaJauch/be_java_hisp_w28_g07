package com.api.social_meli.repository.impl;

import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements IUserRepository {
    public UserRepositoryImpl() throws IOException{
        loadDataBase();
    }

    protected void loadDataBase() {
        try {
            File file;
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> objects;
            file = ResourceUtils.getFile("classpath:users.json");
            objects = objectMapper.readValue(file,new TypeReference<List<User>>(){});
            this.entities = objects;
        }
        catch(IOException ignored) {
        }
    }

    public List<Integer> getFollowedsByUserId(int userId) {
        return entities.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst().get().getFollowed();
    }
}
