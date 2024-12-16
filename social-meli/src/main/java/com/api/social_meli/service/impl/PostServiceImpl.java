package com.api.social_meli.service.impl;

import com.api.social_meli.dto.PromoPostDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.Post;
import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public PromoPostDto getPromoProductCount(Integer userId){
        List<Post> postList = postRepository.findAll();
        List<Post> postByUser = postList.stream().filter(p-> p.getSeller().getUserId() == userId && p.isHasPromo()).toList();
        if (postByUser.isEmpty()) {
            throw new NotFoundException("No se encontraron publicaciones con productos promocionados para el usuario: " + userId);
        }
        String userName = postByUser.getFirst().getSeller().getName();
        return new PromoPostDto(userId,userName, postByUser.size());
    }

}
