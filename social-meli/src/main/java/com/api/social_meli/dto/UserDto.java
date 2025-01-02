package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String name;
    private List<Integer> followed;
    private List<Integer> followers;
    private List<Integer> posts;
}
