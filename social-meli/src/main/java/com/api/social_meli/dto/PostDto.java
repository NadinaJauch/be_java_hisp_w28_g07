package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class PostDto {
    @JsonProperty("post_id")
    private int postId;
    @Min(value = 1, message = "El 'user_id' debe ser mayor a cero")
    @NotNull(message = "El 'user_id' no puede estar vacio")
    @JsonProperty("user_id")
    private Integer userId;
    @NotNull(message = "La fecha en 'date' no puede estar vacía")
    @JsonProperty("date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publishDate;
    @Valid
    private ProductDto product;
    @Min(value = 1, message = "El campo 'category' debe ser mayor que cero")
    @NotNull(message = "El campo 'category' no puede estar vacio")
    @JsonProperty("category")
    private int categoryId;
    private double price;

}
