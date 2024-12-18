package com.api.social_meli.service;

import com.api.social_meli.dto.*;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.FollowedListDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.GetFollowerCountDto;

import java.util.List;

public interface IUserService {

    GetFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    FollowedListDto getFollowedsOrderedByName(int userId, String order);
    boolean followUser(int userId, int userIdToFollow);
    List<UserDto> searchAllUsers();
    FollowerListDto getFollowersOrderedByName (int userId, String order);
}
