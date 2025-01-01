package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoPostDto {
    @JsonProperty("post_id")
    private int postId;
    @Min(value = 0, message = "El id debe ser mayor a cero")
    //TODO: Ints por ser primitivos no pueden ser nulls, siempre seran ceros al no tener un valor declarado. Mencionar al team de que existe la chance de que haya que refactorizar todos los ints de DTOs a Integers.
    @JsonProperty("user_id")
    private Integer seller;
    @NotBlank(message = "La fecha no puede estar vacía")
    @JsonProperty("date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publishDate;
    private ProductDto product;
    @Min(value = 0, message = "El campo no puede estar vacio")
    @JsonProperty("category")
    private int categoryId;
    @DecimalMin(value = "0.0", message = "El campo no puede estar vacio")
    @DecimalMax(value = "10000000.0", message = "El precio maximo por producto es de 10.000.000")
    private double price;
    @JsonProperty("has_promo")
    private boolean hasPromo;
    private double discount;

}
