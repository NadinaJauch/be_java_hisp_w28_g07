package com.api.social_meli.service;

import com.api.social_meli.dto.PromoPostCountDto;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PromoPostDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.PromoPostDto;

import java.util.List;

public interface IPostService {
    List<PostDto> getPostsByUserId(int userId);
    PromoPostCountDto getPromoProductCount(Integer userId);
    String createPromoPost(PromoPostDto dto);
    String createPost(PostDto dto);
    FollowedSellerPostsDto getFollowedSellersPosts(int userId, String order);
}
