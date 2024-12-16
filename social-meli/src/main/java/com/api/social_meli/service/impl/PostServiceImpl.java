package com.api.social_meli.service.impl;

import com.api.social_meli.dto.PostDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.model.Post;
import com.api.social_meli.model.Product;
import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.repository.IProductRepository;
import com.api.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String createPost(PostDto dto) {
        Post toRegister = new Post(dto);
        toRegister.setPostId(postRepository.findAll().size()+1);
        if (validatePost(toRegister)){
            postRepository.create(toRegister);
            return "Post realizado con exito";
        }
        throw new BadRequestException("No se ha podido realizar el post");
    }

    ///
    private boolean validatePost(Post post){
        return post.getPublishDate() != null
                && post.getProduct() != null
                && post.getProduct().getType() != null
                && post.getProduct().getColour() != null
                && post.getProduct().getName() != null
                && post.getProduct().getBrand() != null;
    }
}