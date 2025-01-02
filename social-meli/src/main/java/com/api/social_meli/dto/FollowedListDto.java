package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FollowedListDto {
    @Min(value = 1, message = "El id debe ser mayor a cero")
    @NotNull(message = "El id no puede estar vacio")
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String name;
    private List<FollowDto> followed;
}
