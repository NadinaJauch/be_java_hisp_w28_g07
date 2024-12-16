package com.api.social_meli.service.impl;

import com.api.social_meli.dto.PostDto;
import com.api.social_meli.model.Post;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.repository.impl.UserRepositoryImpl;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;
    public UserServiceImpl(IUserRepository userRepository){
        this.userRepository = userRepository;
    }



}
