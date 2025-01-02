package com.api.social_meli.controller;

import com.api.social_meli.dto.FavouritePostRequestDto;
import com.api.social_meli.dto.GetFavouritePostsResponseDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowerCount(
            @Valid @PathVariable int userId) {
        return new ResponseEntity<>(userService.getFollowerCount(userId),HttpStatus.OK);
    }

    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(
            @Valid @PathVariable int userId,
            @Valid @PathVariable int userIdToUnfollow) {
        return new ResponseEntity<>(userService.unfollowUser(userId,userIdToUnfollow), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(
            @Valid @PathVariable int userId,
            @Valid @RequestParam (required = false) String order) {
        return new ResponseEntity<>(userService.getFollowersOrderedByName(userId, order), HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/list")
    public ResponseEntity<?> getFollowedsByUserId(
            @Valid @PathVariable int userId,
            @Valid @RequestParam(required = false) String order){
        return new ResponseEntity<>(userService.getFollowedsOrderedByName(userId, order), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(
            @Valid @PathVariable("userId") int userId,
            @Valid @PathVariable("userIdToFollow") int userIdToFollow) {
        return new ResponseEntity<>(userService.followUser(userId, userIdToFollow), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){
        return new ResponseEntity<>(userService.searchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/favourites")
    public ResponseEntity<MessageDto> favouritePost(
            @Valid @RequestBody FavouritePostRequestDto request) {
        return ResponseEntity.ok(userService.favouritePost(request));
    }

    @GetMapping("/{userId}/favourites")
    public ResponseEntity<GetFavouritePostsResponseDto> getFavouritePosts(
            @Valid @PathVariable("userId") int userId) {
        return ResponseEntity.ok(userService.getFavouritePosts(userId));
    }


    @PostMapping("/unfavourite")
    public ResponseEntity<?> unfavouritePost(
            @Valid @RequestBody FavouritePostRequestDto request) {
        return new ResponseEntity<>(userService.unfavouritePost(request), HttpStatus.OK);
    }
}
