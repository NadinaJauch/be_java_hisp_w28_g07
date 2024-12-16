package com.api.social_meli.controller;


import com.api.social_meli.dto.PostDto;
import com.api.social_meli.service.IPostService;
import com.api.social_meli.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private IPostService postService;
    private IProductService productService;

    public ProductController(IPostService postService, IProductService productService){
        this.postService = postService;
        this.productService = productService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto dto){
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.OK);
    }


}
