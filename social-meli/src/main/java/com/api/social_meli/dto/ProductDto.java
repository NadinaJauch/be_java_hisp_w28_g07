package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank
    @JsonProperty("product_id")
    private Integer productId;
    @NotBlank(message = "El campo no puede estar vacio")
    @Size(min = 0, max = 40, message = "La longitud no puede superar los 40 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales")
    @JsonProperty("product_name")
    private String name;
    @NotBlank(message = "El campo no puede estar vacio")
    @Size(min = 0, max = 15, message = "La longitud no puede superar los 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "El campo no puede poseer caracteres especiales")
    private String type;
    @NotBlank(message = "El campo no puede estar vacio")
    @Size(min = 0, max = 25, message = "La longitud no puede superar los 25 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "El campo no puede poseer caracteres especiales")
    private String brand ;
    @NotBlank(message = "El campo no puede estar vacio")
    @Size(min = 0, max = 15, message = "La longitud no puede superar los 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "El campo no puede poseer caracteres especiales")
    @JsonProperty("color")
    private String colour;
    @Size(min = 0, max = 80, message = "La longitud no puede superar los 80 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales")
    private String notes;
}
