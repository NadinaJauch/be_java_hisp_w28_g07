package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @JsonProperty("product_id")
    private int productId;
    private String name;
    private String type;
    private String brand ;
    private String colour;
    private String notes;
}
