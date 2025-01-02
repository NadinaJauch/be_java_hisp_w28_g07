package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Data
public class FollowedSellerPostsDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank
    @JsonProperty("user_id")
    private Integer userId;
    private List<PostDto> posts;
}
