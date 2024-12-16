package com.api.social_meli.service.impl;

import com.api.social_meli.dto.*;
import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.GetFollowedsByUserIdDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.dto.GetFollowerCountDto;

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
import java.util.stream.Collectors;

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
        else if (!user.getFollowed().contains(userToUnfollow.getUserId())) {
            throw new BadRequestException("El usuario: " + userId + " no sigue al usuario: " + userIdToUnfollow);
        }
        user.getFollowed().remove(userToUnfollow.getUserId());
        userToUnfollow.getFollowers().remove(user.getUserId());
        return new MessageDto("El usuario se dejo de seguir exitosamente");
    }

    @Override
    public FollowerListDto getFollowersList(int userId) {

        User searchedUser = userRepository.findById(userId);

        if(searchedUser == null){
            throw new NotFoundException("No existe un usuario con el id: " + userId );
        }
        if(!searchedUser.isSeller()){
            throw new BadRequestException("El usuario no es un vendedor y no puede tener seguidores");
        }
        /** Creo el objeto dtoFollowerList y le asigno el id y el nombre del usuario(vendedor) del que estoy buscando
         * los seguidores*/
        FollowerListDto followerListDto = new FollowerListDto();
        followerListDto.setUserId(searchedUser.getUserId());
        followerListDto.setUserName(searchedUser.getName());

        /** Mapeo la lista usuarios seguidores a DtoFollower  */

        List<FollowerDto> followerDtos = searchedUser.getFollowers().stream()
                .map(followerId -> mapper.convertValue(userRepository.findById(followerId), FollowerDto.class))
                .toList();

        /** Seteo la lista de followers con los followersdto  */

        followerListDto.setFollowers(followerDtos);

        return followerListDto;
    }

    public FollowedSellerPostsDto getFollowedSellersPosts(int userId) {
        if (userRepository.exists(userId))
            throw new NotFoundException("No se encontró ningún usuario con ese ID.");

        List<PostDto> posts = getFollowedSellersByUserId(userId)
                .stream()
                .flatMap(seller -> postService.getPostsByUserId(seller.getUserId()).stream())
                .filter(post -> post.getDate() != null &&
                        !post.getDate().isBefore(LocalDate.now().minusWeeks(2)))
                .toList();

        return new FollowedSellerPostsDto(userId,posts);
    }

    private List<User> getFollowedSellersByUserId(int userId){
        List<User> asd = userRepository.findAll();
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getUserId() == userId && x.isSeller())
                .toList();
    }
}
