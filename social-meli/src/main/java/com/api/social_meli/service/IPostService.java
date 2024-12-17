package com.api.social_meli.service;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PromoPostDto;
import com.api.social_meli.dto.PostDto;
import java.util.List;

public interface IPostService {
    List<PostDto> getPostsByUserId(int userId);
    PromoPostDto getPromoProductCount(Integer userId);
    FollowedSellerPostsDto getFollowedSellersPosts(int userId);
    FollowedSellerPostsDto getProductsSortedByDate(int userId, String order);

}
