package com.api.social_meli.service.impl;

import com.api.social_meli.dto.GetFollowedsByUserIdDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.UserDto;
import com.api.social_meli.dto.getFollowerCountDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    public List<GetFollowedsByUserIdDto> getFollowedsByUserId(int userId) {
        //Busco que el usuario exista
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new NotFoundException("Usuario " + userId + " no encontrado");
        }

        //Traigo los id de los followeds
        List<Integer> listFolleweds = userRepository.getFollowedsByUserId(userId);
        if (listFolleweds.isEmpty())
            throw new NotFoundException("El usuario " + userId + " no sigue a ningún vendedor.");

        //Armo y mappeo la lista de followeds
        List<User> listaUsuarios = new ArrayList<>();
        for (int follower : listFolleweds)
            listaUsuarios.add(userRepository.findById(follower));

        return mapper.convertValue(listaUsuarios, new TypeReference<List<GetFollowedsByUserIdDto>>() {});
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

}
