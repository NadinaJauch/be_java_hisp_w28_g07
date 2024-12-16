package com.api.social_meli.service.impl;

import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    protected IPostRepository postRepository;

    @Autowired
    protected ObjectMapper objectMapper;

}
