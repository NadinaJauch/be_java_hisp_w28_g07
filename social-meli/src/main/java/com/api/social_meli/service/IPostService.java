package com.api.social_meli.service;

import com.api.social_meli.dto.*;
import com.api.social_meli.dto.PromoPostDto;

import java.util.List;

public interface IPostService {
    PostDto getPostById(int postId);
    List<PostDto> getPostsByUserId(int userId);
    PromoPostCountDto getPromoProductCount(Integer userId);
    String createPromoPost(PromoPostDto dto);
    String createPost(PostDto dto);
    FollowedSellerPostsDto getFollowedSellersPosts(int userId, String order);
    List<GetByCategoryDto> getPostByCategoryId(int categoryId, double price_min, double price_max);
}
