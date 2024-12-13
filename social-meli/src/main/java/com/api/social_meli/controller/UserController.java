package com.api.social_meli.controller;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/followed/{userId}/list")
    public ResponseEntity<FollowedSellerPostsDto> getFollowedSellersPosts(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getFollowedSellersPosts(userId));
    }
}
