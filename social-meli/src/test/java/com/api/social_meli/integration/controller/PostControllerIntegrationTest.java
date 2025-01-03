package com.api.social_meli.integration.controller;

import com.api.social_meli.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;


@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

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
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description").value(containsString("El 'user_id' debe ser mayor a cero")));
    }

    @Test
    @DisplayName("1.2: CreatePromoPost BadRequest (tira todas las validaciones)")
    public void createPromoPostStressTest() throws Exception{

        //ARRANGE
        PromoPostDto toRegister = new PromoPostDto(
                1,
                -1,
                null,
                new ProductDto(
                        null,
                        "Silla Gamer!·$!%%%!%%%!%!%%!%!%!%!%!!%!!%%!%!%%!",
                        "Gamer AAAAAAAAA())()AAAAA((((·····(((",
                        "Racer OOOOOOOOOOC!))!((()))=)==)))",
                        "Red AND Black UUUUUUUUUUUU!U!!·UU!·U·!U·!U·U!U!·U·U·",
                        "Special Edition )))))))))))))))!!!!!!!!!!???????????)))==)))AAAAAAAAAAAOOOOOOOOOAAA"
                ),
                -1,
                1111111111111.1,
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
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description").value(containsString("El 'user_id' debe ser mayor a cero")))
                .andExpect(jsonPath("$.description").value(containsString("La fecha en 'date' no puede estar vacía")))
                .andExpect(jsonPath("$.description").value(containsString("El 'product_id' no puede estar vacio")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'product_name' no puede poseer caracteres especiales")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'type' no puede poseer caracteres especiales")))
                .andExpect(jsonPath("$.description").value(containsString("La longitud del campo 'type' no puede superar los 15 caracteres")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'brand' no puede poseer caracteres especiales")))
                .andExpect(jsonPath("$.description").value(containsString("La longitud del campo 'brand' no puede superar los 25 caracteres")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'color' no puede poseer caracteres especiales")))
                .andExpect(jsonPath("$.description").value(containsString("La longitud del campo 'color' no puede superar los 15 caracteres")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'notes' no puede poseer caracteres especiales")))
                .andExpect(jsonPath("$.description").value(containsString("La longitud del campo 'notes' no puede superar los 80 caracteres")))
                .andExpect(jsonPath("$.description").value(containsString("El campo 'category' debe ser mayor que cero")))
                .andExpect(jsonPath("$.description").value(containsString("El precio maximo por producto es de 10.000.000")))
        ;
    }



    @Test
    public void createPostTestOk() throws Exception {
        //ARRANGE
        PostDto postDto = new PostDto(6,
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
                1500.50);
        String jsonRequest = objectMapper.writer().writeValueAsString(postDto);

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("Post realizado con exito")));

        //ACT & ASSERT
        mockMvc.perform(post("/products/post").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                statusExpected, contentTypeExpected, bodyExpected
        );




    }

    @Test
    public void createPostTestBadRequest() throws Exception {
        //ARRANGE
        PostDto postDto = new PostDto(2,
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
                1500.50);
        String jsonRequest = objectMapper.writer().writeValueAsString(postDto);

        ResultMatcher statusExpected= status().isBadRequest();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("El post ya existe")));

        //ACT & ASSERT
        mockMvc.perform(post("/products/post").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                statusExpected, contentTypeExpected, bodyExpected
        );

    }



    @Test
    public void getPostByCategoryIdTestOk() throws Exception {
        //ARRANGE
        int categoryId = 3;
        int price_min= 100;
        int price_max= 200;

        //ACT & ASSERT
        GetByCategoryResponseDto responseDto = new GetByCategoryResponseDto("Accessories",List.of(
                new PostDto(
                        2,
                        3,
                        LocalDate.parse("13-12-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        new ProductDto(
                                3,
                                "Wireless Headphones",
                                "Accessories",
                                "Sony",
                                "Black",
                                "Noise cancellation, 30-hour battery"
                        ),
                        3,
                        199.99
                )
        ));

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(responseDto));

        //ACT & ASSERT
        mockMvc.perform(get("/posts/{categoryId}/category/list/{price_min}/{price_max}", categoryId, price_min, price_max)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        statusExpected,
                        contentTypeExpected,
                        bodyExpected
                );

    }


    @Test
    public void getPostByCategoryIdTestNotFound() throws Exception {
        //ARRANGE
        int categoryId = 1;
        int price_min= 100;
        int price_max= 200;


        ResultMatcher statusExpected= status().isNotFound();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(new MessageDto("No se encontraron post con ese id o rango de precio")));

        //ACT & ASSERT
        mockMvc.perform(get("/posts/{categoryId}/category/list/{price_min}/{price_max}", categoryId, price_min, price_max)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        statusExpected,
                        contentTypeExpected,
                        bodyExpected
                );

    }


    @Test
    public void getPromoProductCountTestOk() throws Exception {
        //ARRANGE
        int user_id = 3;
        PromoPostCountDto promoPostCountDto = new PromoPostCountDto(3,"María López",2);

        ResultMatcher statusExpected= status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(promoPostCountDto));

        //ACT & ASSERT
        mockMvc.perform(get("/products/promo-post/count")
                        .param("user_id", String.valueOf(user_id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        statusExpected,
                        contentTypeExpected,
                        bodyExpected
                );



    }

}
