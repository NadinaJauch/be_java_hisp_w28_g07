package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        ResultMatcher statusExpected= status().isNotFound();
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

    //region GET FOLLOWS LISTS
    //region FOLLOWEDS
    @Test
    public void getFollowedsOrderedByNameInvalidOrderBadRequestException() throws Exception{
        //Arrange
        Integer userId = 1;
        String order = "name";
        ExceptionDto exceptionDto = new ExceptionDto("No es un ordenamiento válido.");

        ResultMatcher statusExpected = status().isBadRequest();
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
    public void getFollowersOrderedByNameInvalidOrderBadRequestException() throws Exception{
        //Arrange
        Integer userId = 3;
        String order = "name";
        ExceptionDto exceptionDto = new ExceptionDto("No es un ordenamiento válido.");

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
