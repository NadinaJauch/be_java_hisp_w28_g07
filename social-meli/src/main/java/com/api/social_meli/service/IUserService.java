package com.api.social_meli.service;


import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.getFollowerCountDto;
import com.api.social_meli.dto.FollowedSellerPostsDto;

public interface IUserService {
    FollowedSellerPostsDto getFollowedSellersPosts(int userId);
    getFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);


}
