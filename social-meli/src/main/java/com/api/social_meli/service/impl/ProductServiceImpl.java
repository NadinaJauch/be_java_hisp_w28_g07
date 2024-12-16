package com.api.social_meli.service.impl;

import com.api.social_meli.repository.IProductRepository;
import com.api.social_meli.service.IPostService;
import com.api.social_meli.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPostService postService;
}