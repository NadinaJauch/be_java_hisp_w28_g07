package com.api.social_meli.unit.service;

import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.impl.UserRepositoryImpl;
import com.api.social_meli.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    //region FOLLOW USER
    @Test
    @DisplayName("Intentar seguir a usuario existente")
    void shouldFollowUserSuccessfullyWhenUserExists(){
        // ARRANGE
        int userId = 2;
        int userIdToFollow = 3;

        User user = new User();
        user.setId(userId);
        user.setFollowed(new ArrayList<>());

        User userToFollow = new User();
        userToFollow.setId(userIdToFollow);
        userToFollow.setFollowers(new ArrayList<>());
        userToFollow.setPosts(new ArrayList<>(List.of(1)));

        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findById(userIdToFollow)).thenReturn(userToFollow);

        //ACT
        MessageDto response = userService.followUser(userId, userIdToFollow);

        //ASSERT
        assertEquals("El usuario se comenzo a seguir exitosamente", response.getMessage());
        assertTrue(user.getFollowed().contains(userIdToFollow), "El usuario debería estar en la lista de seguidos.");
        assertTrue(userToFollow.getFollowers().contains(userId), "El usuario seguidor debería estar en la lista de seguidores.");
    }

    @Test
    @DisplayName("Intentar seguir a usuario no existente")
    void shouldNotFollowUserWhenNonexistentUser(){
        // ARRANGE
        int userId = 2;
        int userIdToFollow = 3000;

        User user = new User();
        user.setId(userId);
        user.setFollowed(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findById(userIdToFollow)).thenReturn(null);

        // ACT & ASSERT
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            userService.followUser(userId, userIdToFollow);
        });

        assertEquals("Usuario o vendedor no encontrado", thrown.getMessage());
    }
    //endregion

}
