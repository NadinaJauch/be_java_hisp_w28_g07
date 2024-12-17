package com.api.social_meli.service;

import com.api.social_meli.dto.*;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.GetFollowedsByUserIdDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.GetFollowerCountDto;

import java.util.List;

public interface IUserService {

    GetFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    FollowerListDto getFollowersList(int userId);
    List<GetFollowedsByUserIdDto> getFollowedsByUserId(int userId);
    boolean followUser(int userId, int userIdToFollow);
    List<UserDto> searchAllUsers();
}
