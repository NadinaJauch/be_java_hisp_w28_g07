package com.api.social_meli.service.impl;


import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PromoPostDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.Post;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IPostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    public PromoPostDto getPromoProductCount(Integer userId){
        List<Post> postList = postRepository.findAll();
        List<Post> postByUser = postList.stream().filter(p-> p.getSeller().getUserId() == userId && p.isHasPromo()).toList();
        if (postByUser.isEmpty()) {
            throw new NotFoundException("No se encontraron publicaciones con productos promocionados para el usuario: " + userId);
        }
        String userName = postByUser.getFirst().getSeller().getName();
        return new PromoPostDto(userId,userName, postByUser.size());
    }

    public FollowedSellerPostsDto getFollowedSellersPosts(int userId, String order) {
        if (!userRepository.exists(userId))
            throw new NotFoundException("No se encontró ningún usuario con ese ID.");

        List<PostDto> posts = userRepository.findAll()
                .stream()
                .filter(x -> x.getFollowers().stream().anyMatch(followerId -> followerId.equals(userId)))
                .flatMap(x -> getPostsByUserId(x.getId()).stream())
                .filter(post -> post.getPublishDate() != null &&
                        !post.getPublishDate().isBefore(LocalDate.now().minusWeeks(2)))
                .toList();

        if(order == null)
            return new FollowedSellerPostsDto(userId, posts);

        if(!order.equals("date_desc") && !order.equals("date_asc"))
            throw new BadRequestException("Tipo de order no válido, ingrese date_asc o date_desc");

        return new FollowedSellerPostsDto(userId, getSortedPost(posts,order));
    }

    @Override
    public List<PostDto> getPostsByUserId(int userId) {
        return objectMapper.convertValue(postRepository.findByUserId(userId), new TypeReference<List<PostDto>>() {});
    }

    private List<PostDto> getSortedPost(List<PostDto> posts, String order){
        return  posts.stream()
                .sorted("date_desc".equals(order)
                        ? Comparator.comparing(PostDto::getPublishDate).reversed()
                        : Comparator.comparing(PostDto::getPublishDate))
                .toList();
    }
}
