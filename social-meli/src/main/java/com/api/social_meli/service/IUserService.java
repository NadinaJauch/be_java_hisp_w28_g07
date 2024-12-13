package com.api.social_meli.service;

import com.api.social_meli.dto.FollowedSellerPostsDto;

public interface IUserService {
    public FollowedSellerPostsDto getFollowedSellersPosts(int userId);
}
