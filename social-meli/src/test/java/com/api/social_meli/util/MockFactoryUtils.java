package com.api.social_meli.util;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.UserDto;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockFactoryUtils {

    //region ENTITIES MOCK
    public List<Product> getProductsMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        file = ResourceUtils.getFile("classpath:products.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<Product>>() {});
    }

    public static List<Post> getPostsMock() throws IOException {
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

    public static List<User> getUsersMock() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        file = ResourceUtils.getFile("classpath:users.json"); // obtencion de archivo

        // una reflexion para obtener el tipo generico de esta clase base
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }
    //endregion

    //region USER CREATION
    public static User createUserWithOnlyFollowersAndFolloweds(int userId, List<Integer> followed, List<Integer> followers) {

        return new User(userId,"test",followed,followers,new ArrayList<>(),new ArrayList<>());

    }
    //endregion

    public static void setPostsAsPostedOneWeekAgo(List<Post> postsMock, List<Integer> postIds) {
        postIds.forEach(postId ->
                postsMock.stream()
                        .filter(post -> post.getPostId() == postId)
                        .forEach(post -> post.setPublishDate(LocalDate.now().minusWeeks(1)))
        );
    }

    public static void setPostsFromFollowedUsersAsPostedThreeWeeksAgo(List<Post> postMock, List<Integer> followedUserIds) {
        followedUserIds.forEach(followedUserId ->
                postMock.stream()
                        .filter(post -> post.getUserId() == followedUserId)
                        .forEach(post -> post.setPublishDate(LocalDate.now().minusWeeks(3))));
    }

    public static List<Post> filterPostsByUserId(List<Post> postsMock, int userId) {
        return postsMock.stream()
                .filter(post -> post.getUserId() == userId)
                .toList();
    }

    public static List<Integer> getFollowedsByUserId(List<User> userMocks, int userId) {
        return userMocks.stream()
                .filter(x -> x.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe el user con ese id entre los mocks."))
                .getFollowed();
    }

    public static Boolean getMockPostOrderAsc(List<PostDto> sortedPosts) {
        for (int i = 1; i < sortedPosts.size(); i++) {
            if (sortedPosts.get(i).getPublishDate().isBefore(sortedPosts.get(i - 1).getPublishDate())) {
                return false;
            }
        }
        return true;
    }

    public static Boolean getMockPostOrderDesc(List<PostDto> sortedPosts) {
        for (int i = 1; i < sortedPosts.size(); i++) {
            if (sortedPosts.get(i).getPublishDate().isAfter(sortedPosts.get(i - 1).getPublishDate())) {
                return false;
            }
        }
        return true;
    }




}
