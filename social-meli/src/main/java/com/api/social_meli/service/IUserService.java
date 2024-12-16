package com.api.social_meli.service;


import com.api.social_meli.dto.FollowerDto;
import com.api.social_meli.dto.FollowerListDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.getFollowerCountDto;

public interface IUserService {

    getFollowerCountDto getFollowerCount(int userId);
    MessageDto unfollowUser(int userId, int userIdToUnfollow);
    FollowerListDto getFollowersList(int userId);



}
