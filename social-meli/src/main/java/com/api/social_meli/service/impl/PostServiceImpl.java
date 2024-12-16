package com.api.social_meli.service.impl;

import com.api.social_meli.dto.PromoPostCountDto;
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

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public PromoPostCountDto getPromoProductCount(Integer userId){
        List<Post> postList = postRepository.findAll();
        List<Post> postByUser = postList.stream().filter(p-> p.getSeller().getUserId() == userId && p.isHasPromo()).toList();
        if (postByUser.isEmpty()) {
            throw new NotFoundException("No se encontraron publicaciones con productos promocionados para el usuario: " + userId);
        }
        String userName = postByUser.get(0).getSeller().getName();
        return new PromoPostCountDto(userId,userName, postByUser.size());
    }

    @Override
    public String createPromoPost(PromoPostDto dto) {
        Post toRegister = objectMapper.convertValue(dto, Post.class);
        toRegister.setSeller(userRepository.findById(dto.getSeller()));
        if(validatePost(toRegister)){

            postRepository.create(toRegister);
            return "Post con promo realizado con exito";
        }
        throw new BadRequestException("No se ha podido realizar el post");
    }

    private boolean validatePost(Post post){
        return post.getPublishDate() != null
                && post.getProduct() != null
                && post.getProduct().getType() != null
                && post.getProduct().getColour() != null
                && post.getProduct().getName() != null
                && post.getProduct().getBrand() != null;
    }

    @Override
    public List<PostDto> getPostsByUserId(int userId) {
        return objectMapper.convertValue(postRepository.findByUserId(userId), new TypeReference<List<PostDto>>() {});
    }
}
