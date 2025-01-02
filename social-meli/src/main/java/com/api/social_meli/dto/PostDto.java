package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    @JsonProperty("post_id")
    private int postId;
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank
    @JsonProperty("user_id")
    private Integer userId;
    @NotBlank(message = "La fecha no puede estar vacía")
    @JsonProperty("date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publishDate;
    private ProductDto product;
    @Min(value = 0, message = "El campo no puede estar vacio")
    @JsonProperty("category")
    private int categoryId;
    private double price;

}
