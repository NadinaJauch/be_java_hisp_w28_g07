package com.api.social_meli.controller;

import com.api.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowerCount(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getFollowerCount(userId),HttpStatus.OK);
    }
}
