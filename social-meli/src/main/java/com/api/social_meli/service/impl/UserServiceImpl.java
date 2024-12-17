package com.api.social_meli.service.impl;

import com.api.social_meli.dto.*;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IPostService;
import com.api.social_meli.service.IUserService;
import com.fasterxml.jackson.core.type.TypeReference;
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
    public List<FollowerDto> getFollowersOrderedByName(int userId, String order) {
        //Busco que el usuario exista
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new NotFoundException("Usuario " + userId + " no encontrado");
        }

        //Traigo los id de los followeds
        List<Integer> listFollewers = userRepository.getFollowersByUserId(userId);
        if (listFollewers.isEmpty())
            throw new NotFoundException("El usuario " + userId + " no sigue a ningún vendedor.");

        //Armo y mappeo la lista de followeds
        List<User> listaUsuarios = new ArrayList<>();
        for (int follower : listFollewers)
            listaUsuarios.add(userRepository.findById(follower));

        switch (order) {
            case "name_asc":
                listaUsuarios.sort((user1, user2)-> user1.getName().compareTo(user2.getName()));
                break;
            case "name_desc":
                listaUsuarios.sort((user1, user2)-> user2.getName().compareTo(user1.getName()));
                break;
        }

        return mapper.convertValue(listaUsuarios, new TypeReference<List<FollowerDto>>() {});
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
        for (int followed : listFolleweds)
            listaUsuarios.add(userRepository.findById(followed));

        return mapper.convertValue(listaUsuarios, new TypeReference<List<GetFollowedsByUserIdDto>>() {});
    }

    @Override
    public List<GetFollowedsByUserIdDto> getFollowedsOrderedByName(int userId, String order){
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

        switch (order) {
            case "name_asc":
                listaUsuarios.sort((user1, user2)-> user1.getName().compareTo(user2.getName()));
                break;
            case "name_desc":
                listaUsuarios.sort((user1, user2)-> user2.getName().compareTo(user1.getName()));
                break;
        }

        return mapper.convertValue(listaUsuarios, new TypeReference<List<GetFollowedsByUserIdDto>>() {});
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

        user.getFollowed().remove(userToUnfollow.getUserId());
        userToUnfollow.getFollowers().remove(user.getUserId());
        return new MessageDto("El usuario se dejo de seguir exitosamente");
    }

    @Override
    public FollowerListDto getFollowersList(int userId) {
        User searchedUser = userRepository.findById(userId);

        if(searchedUser == null)
            throw new NotFoundException("No existe un usuario con el id: " + userId );

        if(!searchedUser.isSeller())
            throw new BadRequestException("El usuario no es un vendedor y no puede tener seguidores");

        List<FollowerDto> followerDtos = searchedUser.getFollowers().stream()
                .map(followerId -> mapper.convertValue(userRepository.findById(followerId), FollowerDto.class))
                .toList();

        return new FollowerListDto(searchedUser.getId(),searchedUser.getName(),followerDtos);
    }

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
}
