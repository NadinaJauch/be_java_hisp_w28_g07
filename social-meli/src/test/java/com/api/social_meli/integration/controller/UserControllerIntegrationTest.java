package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.ExceptionDto;
import com.api.social_meli.dto.GetFollowerCountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        int parametroUserId = 3;
        GetFollowerCountDto dtoEsperado = new GetFollowerCountDto(3,"María López",3);

        ResultMatcher statusEsperado= status().isOk();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(dtoEsperado));

        //ACT && ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", parametroUserId))
                .andExpectAll(
                        statusEsperado, contentTypeEsperado, bodyEsperado
                ).andDo(print());
    }

    @Test
    public void getFollowerCountException() throws Exception {
        //ARRANGE
        int parametroUserId = 60;
        ExceptionDto dtoEsperado = new ExceptionDto("Usuario no encontrado");

        ResultMatcher statusEsperado= status().isNotFound();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(dtoEsperado));

        //ACT && ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", parametroUserId))
                .andExpectAll(
                        statusEsperado, contentTypeEsperado, bodyEsperado
                ).andDo(print());
    }

    //endregion

}
