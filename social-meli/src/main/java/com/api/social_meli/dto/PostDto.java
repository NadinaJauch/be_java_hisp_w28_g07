package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    @Min(value = -1, message = "El id debe ser mayor a cero")
    //TODO: Ints por ser primitivos no pueden ser nulls, siempre seran ceros al no tener un valor declarado. Mencionar al team de que existe la chance de que haya que refactorizar todos los ints de DTOs a Integers.
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publishDate;
    private ProductDto product;
    @JsonProperty("category")
    private int categoryId;
    private double price;

}
