package com.api.social_meli.service;


import com.api.social_meli.dto.getFollowerCountDto;

public interface IUserService {

    getFollowerCountDto getFollowerCount(int userId);


}
