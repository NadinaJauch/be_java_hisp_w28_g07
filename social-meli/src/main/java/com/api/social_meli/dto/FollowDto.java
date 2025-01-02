package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class FollowDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank(message = "")
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String name;
}
