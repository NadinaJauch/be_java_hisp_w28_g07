package com.api.social_meli.controller;

import com.api.social_meli.dto.PostDto;
import com.api.social_meli.service.IPostService;
import com.api.social_meli.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private IPostService postService;
    public ProductController(IPostService postService){
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto dto) {
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.OK);
    }

}
