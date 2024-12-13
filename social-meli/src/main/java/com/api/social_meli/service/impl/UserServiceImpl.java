package com.api.social_meli.service.impl;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public FollowedSellerPostsDto getFollowedSellersPosts(int userId) {
        User user = userRepository.findById(userId);
        if (user == null)
            throw new NotFoundException("No se encontró ningún usuario con ese ID.");

        List<PostDto> posts = user.getFollowed().stream()
                .filter(User::isSeller)
                .flatMap(seller -> seller.getPosts().stream())
                .filter(post -> post.getPublishDate() != null &&
                        !post.getPublishDate().isBefore(LocalDate.now().minusWeeks(2)))
                .map(x ->  objectMapper.convertValue(x,PostDto.class))
                .toList();

        return new FollowedSellerPostsDto(userId,posts);
    }
}