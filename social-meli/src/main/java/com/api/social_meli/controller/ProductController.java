package com.api.social_meli.controller;

import com.api.social_meli.service.IPostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private IPostService postService;
}
