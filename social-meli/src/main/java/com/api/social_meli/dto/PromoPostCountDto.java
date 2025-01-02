package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PromoPostCountDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    //TODO: Ints por ser primitivos no pueden ser nulls, siempre seran ceros al no tener un valor declarado. Mencionar al team de que existe la chance de que haya que refactorizar todos los ints de DTOs a Integers.
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("promo_products_count")
    private Integer promoProductCount;
}
