package com.api.social_meli.service;

import com.api.social_meli.dto.FollowedSellerPostsDto;

import com.api.social_meli.dto.GetFollowedsByUserIdDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.GetFollowerCountDto;

import java.util.List;

public interface IUserService {

    FollowedSellerPostsDto getFollowedSellersPosts(int userId);
    GetFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    List<GetFollowedsByUserIdDto> getFollowedsByUserId(int userId);

}
