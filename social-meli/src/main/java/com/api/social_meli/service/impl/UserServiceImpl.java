package com.api.social_meli.service.impl;

import com.api.social_meli.dto.*;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    public getFollowerCountDto getFollowerCount(int userId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        int followerCount = user.getFollowers().size();
        return new getFollowerCountDto(user.getUserId(), user.getName(), followerCount);
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
        else if (!user.getFollowed().contains(userToUnfollow)) {
            throw new BadRequestException("El usuario: " + userId + " no sigue al usuario: " + userIdToUnfollow);
        }
        user.getFollowed().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(user);
        return new MessageDto("El usuario se dejo de seguir exitosamente");
    }

    @Override
    public FollowerListDto getFollowersList(int userId) {

        User searchedUser = userRepository.findById(userId);

        if(searchedUser == null){
            throw new NotFoundException("No existe un usuario con el id: " + userId );
        }
        if(searchedUser.getPosts().isEmpty()){
            throw new BadRequestException("El usuario no es un vendedor y no puede tener seguidores");
        }
        /** Creo el objeto dtoFollowerList y le asigno el id y el nombre del usuario(vendedor) del que estoy buscando
         * los seguidores*/
        FollowerListDto followerListDto = new FollowerListDto();
        followerListDto.setUserId(searchedUser.getUserId());
        followerListDto.setUserName(searchedUser.getName());

        /** Mapeo la lista usuarios seguidores a DtoFollower  */

        List<FollowerDto> followerDtos = searchedUser.getFollowers().stream()
                .map(followerId -> {
                    User follower = userRepository.findById(followerId);
                    if(follower != null){
                        return new FollowerDto(followerId, follower.getName());
                    } else{
                        throw new NotFoundException("No se encontró el follower con id: " + followerId);
                    }
                })
                .collect(Collectors.toList());

        /** Seteo la lista de followers con los followersdto  */

        followerListDto.setFollowers(followerDtos);

        return followerListDto;
    }

}
