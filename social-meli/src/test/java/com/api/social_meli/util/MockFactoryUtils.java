package com.api.social_meli.util;

import com.api.social_meli.model.Post;
import com.api.social_meli.model.PostCategory;
import com.api.social_meli.model.Product;
import com.api.social_meli.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockFactoryUtils {

    //region ENTITIES MOCK
    public List<Product> getProductsMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        file = ResourceUtils.getFile("classpath:products.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<Product>>() {});
    }

    public List<Post> getPostsMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // para que tome los DateTime

        file = ResourceUtils.getFile("classpath:posts.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<Post>>() {});
    }

    public List<PostCategory> getPostCategoriesMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        file = ResourceUtils.getFile("classpath:postcategory.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<PostCategory>>() {});
    }

    public List<User> getUsersMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        file = ResourceUtils.getFile("classpath:users.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }
    //endregion

    //region USER CREATION
    public User createUserWithOnlyFollowersAndFolloweds(int userId, List<Integer> followed, List<Integer> followers) {

        return new User(userId,"test",followed,followers,new ArrayList<>(),new ArrayList<>());

    }
    //endregion
}
