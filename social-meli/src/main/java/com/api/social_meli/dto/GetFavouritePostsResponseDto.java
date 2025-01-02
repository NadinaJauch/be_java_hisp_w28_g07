package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GetFavouritePostsResponseDto {
    @Min(value = 0, message = "El id debe ser mayor a cero")
    @NotBlank
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("favourite_posts")
    private List<PostDto> favouritePosts;
}
