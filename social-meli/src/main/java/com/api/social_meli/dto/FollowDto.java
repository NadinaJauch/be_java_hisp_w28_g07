package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FollowDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank(message = "")
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String name;
}
