package com.api.social_meli.service;


import com.api.social_meli.dto.GetFollowedsByUserIdDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.getFollowerCountDto;
import com.api.social_meli.model.User;

import java.util.List;

public interface IUserService {

    getFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    List<GetFollowedsByUserIdDto> getFollowedsByUserId(int userId);

}
