package com.api.social_meli.service;

import com.api.social_meli.dto.*;
import com.api.social_meli.model.User;

import java.util.List;

public interface IUserService {

    FollowedSellerPostsDto getFollowedSellersPosts(int userId);
    GetFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    List<GetFollowedsByUserIdDto> getFollowedsByUserId(int userId);
    boolean followUser(int userId, int userIdToFollow);
    List<UserDto> searchAllUsers();
}
