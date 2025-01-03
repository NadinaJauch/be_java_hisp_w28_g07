package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.ExceptionDto;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.ProductDto;
import com.api.social_meli.repository.IPostRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.api.social_meli.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

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

    //region GET FOLLOWED SELLERS POSTS

    //region WITHOUT ORDER
    @Test
    @DisplayName("Obtener posts de vendedores seguidos (sin orden), caso OK lista con posts.")
    @DirtiesContext
    void shouldReturnOkWithContentFollowedSellersPosts() throws Exception {
        // Arrange
        int userId = 1;

        Post post2 = postRepository.findById(2);  // Encuentra el post con ID 2
        Post post4 = postRepository.findById(4);  // Encuentra el post con ID 4
        post2.setPublishDate(LocalDate.now().minusWeeks(1));
        post4.setPublishDate(LocalDate.now().minusWeeks(1));

        List<PostDto> postDtoList = objectMapper.convertValue(List.of(post2, post4),
                new TypeReference<List<PostDto>>() {});

        FollowedSellerPostsDto expectedResult = new FollowedSellerPostsDto(userId, postDtoList);

        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedJson = content().json(objectMapper.writeValueAsString(expectedResult));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedContentType,expectedJson,expectedStatus)
                .andDo(print());
    }

    @Test
    @DisplayName("Obtener posts de vendedores seguidos (sin orden), caso OK lista de posts vacía.")
    @DirtiesContext
    void shouldReturnOkWithEmptyListOfFollowedSellersPosts() throws Exception {
        // Arrange
        int userId = 1;

        postRepository.findAll().forEach(
                post -> post.setPublishDate(LocalDate.now().minusWeeks(3))
        );

        FollowedSellerPostsDto expectedResult = new FollowedSellerPostsDto(userId, List.of());

        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedJson = content().json(objectMapper.writeValueAsString(expectedResult));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list",  userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedContentType,expectedJson,expectedStatus)
                .andDo(print());
    }
    //endregion

    //region EXCEPTIONS
    @Test
    @DisplayName("Obtener posts de vendedores seguidos con userId no existente, caso Not Found.")
    void shouldThrowExNonExistentUserWhenGetFollowedSellersPosts() throws Exception {
        // Arrange
        int userId = 99999;

        ResultMatcher expectedStatus = status().isNotFound();
        ResultMatcher expectedJson = content().json(objectMapper.writeValueAsString(new ExceptionDto("No se encontró ningún usuario con ese ID.")));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list",  userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedContentType,expectedJson,expectedStatus)
                .andDo(print());
    }

    @Test
    @DisplayName("Obtener posts de vendedores seguidos con orden inválido, caso Bad Request.")
    void shouldThrowExNotValidOrderWhenGetFollowedSellersPosts() throws Exception {
        // Arrange
        int userId = 1;
        String order = "not valid order";

        ResultMatcher expectedStatus = status().isBadRequest();
        ResultMatcher expectedJson = content().json(objectMapper.writeValueAsString(new ExceptionDto("Tipo de order no válido, ingrese date_asc o date_desc")));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list", userId)
                        .param("order", order))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedContentType, expectedJson, expectedStatus)
                .andDo(print());
    }
    //endregion

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
