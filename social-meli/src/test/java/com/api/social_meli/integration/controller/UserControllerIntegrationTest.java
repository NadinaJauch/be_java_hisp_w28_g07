package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.ExceptionDto;
import com.api.social_meli.dto.FavouritePostRequestDto;
import com.api.social_meli.dto.GetFollowerCountDto;
import com.api.social_meli.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

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


    //region FAVOURITE POST
    @Test
    public void favouritePostOk() throws Exception {
        //ARRANGE
        String jsonRequest = objectMapper.writer().writeValueAsString(new FavouritePostRequestDto(1,5));

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("Publicación agregada a favoritos exitosamente")));

        //ACT && ASSERT
        mockMvc.perform(post("/users/favourites").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                statusExpected, contentTypeExpected, bodyExpected
        );

    }

    @Test
    public void favouritePostConflictException() throws Exception {
        //ARRANGE
        String jsonRequest = objectMapper.writer().writeValueAsString(new FavouritePostRequestDto(1,2));

        ResultMatcher statusExpected= status().isConflict();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("El post ya está en favoritos")));

        //ACT && ASSERT
        mockMvc.perform(post("/users/favourites").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                statusExpected, contentTypeExpected, bodyExpected
        );
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
}
