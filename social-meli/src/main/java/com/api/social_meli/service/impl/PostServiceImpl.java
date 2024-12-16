package com.api.social_meli.service.impl;

import com.api.social_meli.dto.PostDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.model.Post;
import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;
    public PostServiceImpl(IPostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Override
    public String createPost(PostDto dto) {

        ObjectMapper objectMapper = new ObjectMapper();
        Post toRegister = objectMapper.convertValue(dto, Post.class);// ToDo: aca va una validacion para verificar que el post a registrar no sea nulo, o tenga valores nulos
        if(validatePost(toRegister)){
            if (postRepository.create(toRegister).equals(toRegister)){
                return "Publicacion realizada con exito";
            }
        }
        throw new BadRequestException("No se pudo realizar la publicacion");
    }

    private boolean validatePost(Post post){ //ToDo: esto no va, implementar algo mejor que esto
        if (
                post.getPublishDate() == null
                || post.getProduct() == null
                || post.getProduct().getBrand() == null
                || post.getProduct().getColour() == null
                || post.getProduct().getName() == null
                || post.getProduct().getType() == null
        ){
            return true;
        }
        return false;
    }

}
