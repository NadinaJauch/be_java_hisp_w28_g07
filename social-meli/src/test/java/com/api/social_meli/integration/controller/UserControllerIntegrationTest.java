package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //region GET FOLLOWER COUNT
    @Test
    public void getFollowerCountOk() throws Exception {
        //ARRANGE
        Integer userId = 3;
        GetFollowerCountDto responseExpected = new GetFollowerCountDto(3,"María López",3);

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(responseExpected));

        //ACT && ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", userId))
                .andExpectAll(
                        statusExpected,contentTypeExpected,bodyExpected
                ).andDo(print());
    }

    @Test
    public void getFollowerCountException() throws Exception {
        //ARRANGE
        Integer userId = 60;
        ExceptionDto responseExpected = new ExceptionDto("Usuario no encontrado");

        ResultMatcher statusExpected= status().isNotFound();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(responseExpected));

        //ACT && ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", userId))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }

    //endregion

    //region UNFOLLOW USER
    @Test
    public void unfollowUserOk() throws Exception {

        //ARRANGE
        Integer userId = 6;
        Integer userIdToUnfollow = 2;

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("El usuario se dejo de seguir exitosamente")));

        //ACT && ASSERT
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());

    }

    @Test
    public void unfollowUserBadRequestException() throws Exception {
        Integer userId = 2;
        Integer userIdToUnfollow = 5;

        ResultMatcher statusExpected= status().isBadRequest();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("Actualmente no sigue a ese usuario")));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }
    //endregion

    //region FOLLOW USER
    @Test
    @DisplayName("Seguir usuario exitosamente")
    public void followUserOk() throws Exception {
        // ARRANGE
        Integer userId = 2;
        Integer userIdToFollow = 3;

        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("El usuario se comenzo a seguir exitosamente")));

        // ACT && ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow))
                .andExpectAll(
                        statusExpected,
                        contentTypeExpected,
                        bodyExpected
                ).andDo(print());
    }

    @Test
    @DisplayName("Seguir usuario inexistente")
    public void followUserNotFoundException() throws Exception {
        // ARRANGE
        Integer userId = 2;
        Integer userIdToFollow = 3000;

        ResultMatcher statusExpected = status().isNotFound();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("Usuario o vendedor no encontrado")));

        // ACT && ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow))
                .andExpectAll(
                        statusExpected,
                        contentTypeExpected,
                        bodyExpected
                ).andDo(print());
    }
    //endregion

    //region GET FOLLOWS SORTED LISTS
    //region FOLLOWEDS
    @Test
    public void getFollowedsOrderedByNameOk() throws Exception{
        //Arrange
        Integer userId = 1;
        String order = "name_asc";
        FollowedListDto listSpected = new FollowedListDto(
                1,
                "Ana Martínez",
                new ArrayList<>(
                        List.of(
                                new FollowDto(4, "Juan Pérez"),
                                new FollowDto(5, "Lucía Fernández"),
                                new FollowDto(3, "María López")
                        )
                )
        );

        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(listSpected));

        //Act + Assert
        mockMvc.perform(get("/users/{userId}/followed/list", userId).param("order", order))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }

    @Test
    public void getFollowedsOrderedByNameInvalidUserIdNotFoundException() throws Exception{
        //Arrange
        Integer userId = 1000;
        String order = "name_asc";
        ExceptionDto exceptionDto = new ExceptionDto("No se encontró el usuario.");

        ResultMatcher statusExpected = status().isNotFound();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(exceptionDto));

        //Act + Assert
        mockMvc.perform(get("/users/{userId}/followed/list", userId).param("order", order))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }
    //endregion

    //region FOLLOWERS
    @Test
    public void getFollowersOrderedByNameOk() throws Exception{
        //Arrange
        Integer userId = 3;
        String order = "name_desc";
        FollowerListDto listSpected = new FollowerListDto(
                3,
                "María López",
                new ArrayList<>(
                        List.of(
                                new FollowDto(6, "Miguel Rodríguez"),
                                new FollowDto(5, "Lucía Fernández"),
                                new FollowDto(1, "Ana Martínez")
                        )
                )
        );

        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(listSpected));

        //Act + Assert
        mockMvc.perform(get("/users/{userId}/followers/list", userId).param("order", order))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }

    @Test
    public void getFollowersOrderedByNameInvalidUserIdNotFoundException() throws Exception{
        //Arrange
        Integer userId = 1000;
        String order = "name_desc";
        ExceptionDto exceptionDto = new ExceptionDto("No se encontró el usuario.");

        ResultMatcher statusExpected = status().isNotFound();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(exceptionDto));

        //Act + Assert
        mockMvc.perform(get("/users/{userId}/followers/list", userId).param("order", order))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }

    @Test
    public void getFollowersOrderedByNameISNotSellerBadRequestException() throws Exception{
        //Arrange
        Integer userId = 1;
        String order = "name_desc";
        ExceptionDto exceptionDto = new ExceptionDto("El usuario no es un vendedor y no puede tener seguidores.");

        ResultMatcher statusExpected = status().isBadRequest();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(exceptionDto));

        //Act + Assert
        mockMvc.perform(get("/users/{userId}/followers/list", userId).param("order", order))
                .andExpectAll(
                        statusExpected, contentTypeExpected, bodyExpected
                ).andDo(print());
    }
    //endregion
    //endregion
}
