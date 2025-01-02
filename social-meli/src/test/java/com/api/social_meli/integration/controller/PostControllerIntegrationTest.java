package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.ProductDto;
import com.api.social_meli.dto.PromoPostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1.1: CreatePromnoPost OK")
    public void createPromoPostOK() throws Exception{

        //ARRANGE
        PromoPostDto toRegister = new PromoPostDto(
                1,
                2,
                LocalDate.parse("29-04-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                new ProductDto(
                        1,
                        "Silla Gamer",
                        "Gamer",
                        "Racer",
                        "RedBlack",
                        "Special Edition"
                ),
                2,
                1500.50,
                true,
                0.25
        );
        ObjectWriter writer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(toRegister);

        String expectedMessage = "Post con promo realizado con exito";

        //ACT
        mockMvc.perform(MockMvcRequestBuilders.post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
        //ASSERT
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    @DisplayName("1.2: CreatePromoPost BadRequest (seller_id menor a cero)")
    public void createPromoPostBadRequest() throws Exception{

        //ARRANGE
        PromoPostDto toRegister = new PromoPostDto(
                1,
                -1,
                LocalDate.parse("29-04-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                new ProductDto(
                        1,
                        "Silla Gamer",
                        "Gamer",
                        "Racer",
                        "RedBlack",
                        "Special Edition"
                ),
                2,
                1500.50,
                true,
                0.25
        );
        ObjectWriter writer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(toRegister);

        String expectedMessage = "Creado con exito";

        //ACT
        mockMvc.perform(MockMvcRequestBuilders.post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
        //ASSERT
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
                //.andExpect(jsonPath("$.message").value("Post con promo realizado con exito"));
    }




}
