package com.api.social_meli.controller;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IPostService postService;

    @GetMapping("/promo-post/count")
    public ResponseEntity<?> getPromoProductCount(@RequestParam("user_id") Integer userId) {
        return new ResponseEntity<>(postService.getPromoProductCount(userId), HttpStatus.OK);
    }
    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<FollowedSellerPostsDto> getFollowedSellersPosts(@PathVariable int userId,
                                                                          @RequestParam (required = false)
                                                                                  String order) {
        if(order == null){
            return ResponseEntity.ok(postService.getFollowedSellersPosts(userId));
        }
        return new ResponseEntity<>(postService.getProductsSortedByDate(userId, order), HttpStatus.OK);
    }

}
