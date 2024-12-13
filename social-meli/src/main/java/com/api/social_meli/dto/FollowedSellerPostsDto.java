package com.api.social_meli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FollowedSellerPostsDto {
    private int userId;
    private List<PostDto> posts;
}
