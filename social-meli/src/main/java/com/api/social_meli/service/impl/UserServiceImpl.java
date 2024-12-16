package com.api.social_meli.service.impl;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.GetFollowerCountDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IPostService;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IPostService postService;

    @Autowired
    ObjectMapper mapper;

    public GetFollowerCountDto getFollowerCount(int userId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        int followerCount = user.getFollowers().size();
        return new GetFollowerCountDto(user.getUserId(), user.getName(), followerCount);
    }

    @Override
    public MessageDto unfollowUser(int userId, int userIdToUnfollow) {
        User user = userRepository.findById(userId);
        User userToUnfollow = userRepository.findById(userIdToUnfollow);
        if(user == null) {
            throw new NotFoundException("Usuario " + userId + " no encontrado");
        }
        else if (userToUnfollow == null) {
            throw new NotFoundException("Usuario " + userIdToUnfollow + " no encontrado");
        }
        else if (!user.getFollowed().contains(userToUnfollow.getUserId())) {
            throw new BadRequestException("El usuario: " + userId + " no sigue al usuario: " + userIdToUnfollow);
        }
        user.getFollowed().remove(userToUnfollow.getUserId());
        userToUnfollow.getFollowers().remove(user.getUserId());
        return new MessageDto("El usuario se dejo de seguir exitosamente");
    }

    @Override
    public FollowedSellerPostsDto getFollowedSellersPosts(int userId) {
        if (!userRepository.exists(userId))
            throw new NotFoundException("No se encontró ningún usuario con ese ID.");

        List<PostDto> posts = userRepository.findAll()
                .stream()
                .filter(x -> x.getFollowers().stream().anyMatch(followerId -> followerId.equals(userId)))
                .flatMap(x -> postService.getPostsByUserId(x.getId()).stream())
                .filter(post -> post.getPublishDate() != null &&
                        !post.getPublishDate().isBefore(LocalDate.now().minusWeeks(2)))
                .toList();

        return new FollowedSellerPostsDto(userId,posts);
    }
}