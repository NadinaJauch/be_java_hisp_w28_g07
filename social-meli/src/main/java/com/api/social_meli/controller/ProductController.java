package com.api.social_meli.controller;

import com.api.social_meli.dto.PromoPostDto;
import com.api.social_meli.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IPostService postService;


    @GetMapping("/promo-post/count")
    public ResponseEntity<?> getPromoProductCount(@RequestParam("user_id") Integer userId) {
        return new ResponseEntity<>(postService.getPromoProductCount(userId), HttpStatus.OK);
    }

    @PostMapping("/promo-post")
    public ResponseEntity<?> createPromoPost(@RequestBody PromoPostDto dto) {
        return new ResponseEntity<>(postService.createPromoPost(dto), HttpStatus.OK);
    }



}
