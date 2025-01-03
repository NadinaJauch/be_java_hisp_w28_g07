package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.ExceptionDto;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.ProductDto;
import com.api.social_meli.repository.IPostRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPostRepository postRepository;

    static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

    //region GET FOLLOWED SELLER POSTS
    @Test
    @DisplayName("Posteos de vendedores seguidos ordenados de forma ascendente")
    @DirtiesContext
    public void shouldReturnFollowedSellersPostsAscSortedOk() throws Exception {
        //ARRANGE
        Integer userId = 1;
        String order = "date_asc";

        postRepository.findById(2).setPublishDate(LocalDate.now().minusWeeks(1));
        postRepository.findById(4).setPublishDate(LocalDate.now().minusDays(10));

        FollowedSellerPostsDto dtoExpected = new FollowedSellerPostsDto(
                1,
                new ArrayList<>(
                        List.of(
                                new PostDto(
                                        2,
                                        3,
                                        LocalDate.now().minusWeeks(1),
                                        new ProductDto(3, "Wireless Headphones", "Accessories",
                                                "Sony", "Black", "Noise cancellation, 30-hour battery"),
                                        3,
                                        199.99
                                ),
                                new PostDto(
                                        4,
                                        3,
                                        LocalDate.now().minusDays(10),
                                        new ProductDto(6, "Laptop HP Envy", "Electronics", "HP",
                                                "Silver", "Intel Core i7, 16GB RAM, 512GB SSD"),
                                        1,
                                        1199.99
                                )
                        )
                )
        );

        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(dtoExpected));
        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");

        //ACT && ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", userId)
            .param("order", order))
            .andExpectAll(statusExpected, contentTypeExpected, bodyExpected)
            .andDo(print());
    }
    @Test
    @DisplayName("Posteos de vendedores seguidos ordenados de forma descendente")
    @DirtiesContext
    public void shouldReturnFollowedSellersPostsDescSortedOk() throws Exception {
        //ARRANGE
        Integer userId = 1;
        String order = "date_desc";

        postRepository.findById(2).setPublishDate(LocalDate.now().minusWeeks(1));
        postRepository.findById(4).setPublishDate(LocalDate.now().minusDays(10));

        FollowedSellerPostsDto dtoExpected = new FollowedSellerPostsDto(
                1,
                new ArrayList<>(
                        List.of(
                                new PostDto(
                                        4,
                                        3,
                                        LocalDate.now().minusDays(10),
                                        new ProductDto(6, "Laptop HP Envy", "Electronics", "HP",
                                                "Silver", "Intel Core i7, 16GB RAM, 512GB SSD"),
                                        1,
                                        1199.99
                                ),
                                new PostDto(
                                        2,
                                        3,
                                        LocalDate.now().minusWeeks(1),
                                        new ProductDto(3, "Wireless Headphones", "Accessories",
                                                "Sony", "Black", "Noise cancellation, 30-hour battery"),
                                        3,
                                        199.99
                                )
                        )
                )
        );

        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(dtoExpected));
        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");

        //ACT && ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", userId)
                .param("order", order))
                .andExpectAll(statusExpected, contentTypeExpected, bodyExpected)
                .andDo(print());
    }
    @Test
    @DisplayName("Variable order invalida")
    void shouldThrowInvalidOrderNameWhenGetFollowedSellersPosts() throws Exception {
        //ARRANGE
        Integer userId = 1;
        String order = "invalid";

        ExceptionDto responseExpected = new ExceptionDto("Tipo de order no válido, ingrese date_asc o date_desc");

        ResultMatcher statusExpected = status().isBadRequest();
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(responseExpected));

        //ACT && ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", userId)
                .param("order", order))
                .andExpectAll(statusExpected, bodyExpected)
                .andDo(print());
    }
}
