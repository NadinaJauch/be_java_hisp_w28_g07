package com.api.social_meli.service.impl;

import com.api.social_meli.dto.*;
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
import java.util.ArrayList;
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
    public FollowerListDto getFollowersOrderedByName(int userId, String order) {
        //Verifica que el usuario exista
        User searchedUser = userRepository.findById(userId);
        if(!userRepository.exists(userId))
            throw new NotFoundException("No se encontró el usuario.");

        //Verifica si el usuario es un vendedor
        if(!searchedUser.isSeller())
            throw new BadRequestException("El usuario no es un vendedor y no puede tener seguidores.");

        List<FollowDto> followerDtos = searchedUser.getFollowers().stream()
                .map(followerId -> mapper.convertValue(userRepository.findById(followerId), FollowDto.class))
                .toList();

        if (order != null){
            followerDtos = sortList(followerDtos, order);
        }

        return new FollowerListDto(searchedUser.getId(),searchedUser.getName(), followerDtos);
    }

    @Override
    public FollowedListDto getFollowedsOrderedByName(int userId, String order){
        //Verifica que el usuario exista
        User searchedUser = userRepository.findById(userId);
        if(!userRepository.exists(userId))
            throw new NotFoundException("No se encontró el usuario.");

        List<FollowDto> followedDtos = searchedUser.getFollowed().stream()
                .map(followedId -> mapper.convertValue(userRepository.findById(followedId), FollowDto.class))
                .toList();

        if (order != null){
            followedDtos = sortList(followedDtos, order);
        }

        if (followedDtos.isEmpty())
            throw new NotFoundException("El usuario, no sigue a ningún vendedor.");

        return new FollowedListDto(searchedUser.getId(),searchedUser.getName(), followedDtos);
    }

    @Override
    public MessageDto unfollowUser(int userId, int userIdToUnfollow) {
        User user = userRepository.findById(userId);
        User userToUnfollow = userRepository.findById(userIdToUnfollow);
        if(user == null)
            throw new NotFoundException("Usuario " + userId + " no encontrado");

        if (userToUnfollow == null)
            throw new NotFoundException("Usuario " + userIdToUnfollow + " no encontrado");

        if (!user.getFollowed().contains(userToUnfollow.getUserId()))
            throw new BadRequestException("El usuario: " + userId + " no sigue al usuario: " + userIdToUnfollow);

        user.getFollowed().remove(Integer.valueOf(userToUnfollow.getUserId()) );
        userToUnfollow.getFollowers().remove(Integer.valueOf(user.getUserId()));
        return new MessageDto("El usuario se dejo de seguir exitosamente");
    }

    @Override
    public boolean followUser(int userId, int userIdToFollow) {
        User user = userRepository.findById(userId);
        User userToFollow = userRepository.findById(userIdToFollow);

        if (user == null || userToFollow == null) {
            throw new NotFoundException("Usuario o vendedor no encontrado");
        }
        if (!userToFollow.isSeller()) {
            throw new BadRequestException("El usuario a seguir no es vendedor");
        }
        // Validar si el usuario ya está siguiendo al usuario objetivo
        if (user.getFollowed().contains(userIdToFollow)) {
            throw new BadRequestException("Ya estás siguiendo a este usuario");
        }
        // Agrega el usuario vendedor a la lista del usuario seguidor
        user.getFollowed().add(userIdToFollow);

        // Agrega el usuario seguidor a la lista del usuario vendedor
        if (!userToFollow.getFollowers().contains(userId)) {
            userToFollow.getFollowers().add(userId);
        }
        return true;
    }

    @Override
    public List<UserDto> searchAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            throw new NotFoundException("No se encontró ningun usuario en el sistema.");
        }
        return userList.stream()
                .map(v -> mapper.convertValue(v, UserDto.class))
                .toList();
    }

    private List<FollowDto> sortList (List<FollowDto> list, String order){
        List<FollowDto> sortList = new ArrayList<>(list);
        switch (order) {
            case "name_asc":
                sortList.sort((followed1, followed2) -> followed1.getName().compareTo(followed2.getName()));
                break;
            case "name_desc":
                sortList.sort((followed1, followed2) -> followed2.getName().compareTo(followed1.getName()));
                break;
        }
        return sortList;
    }
}
