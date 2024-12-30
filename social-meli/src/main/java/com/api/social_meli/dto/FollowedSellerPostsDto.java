package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@Data
public class FollowedSellerPostsDto {
    @Min(value = -1, message = "El id debe ser mayor a cero")
    //TODO: Ints por ser primitivos no pueden ser nulls, siempre seran ceros al no tener un valor declarado. Mencionar al team de que existe la chance de que haya que refactorizar todos los ints de DTOs a Integers.
    @JsonProperty("user_id")
    private int userId;
    private List<PostDto> posts;
}
