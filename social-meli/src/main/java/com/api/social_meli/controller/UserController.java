package com.api.social_meli.controller;

import com.api.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowerCount(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getFollowerCount(userId),HttpStatus.OK);
    }

    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        return new ResponseEntity<>(userService.unfollowUser(userId,userIdToUnfollow), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(
            @PathVariable("userId") int userId,
            @PathVariable("userIdToFollow") int userIdToFollow) {
        return new ResponseEntity<>(userService.followUser(userId, userIdToFollow), HttpStatus.OK);
    }
}
