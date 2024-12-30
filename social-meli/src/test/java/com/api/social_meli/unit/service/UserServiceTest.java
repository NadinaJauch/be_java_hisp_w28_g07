package com.api.social_meli.unit.service;

import com.api.social_meli.dto.GetFollowerCountDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.impl.UserRepositoryImpl;
import com.api.social_meli.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    //region T-0002 - US-0007 UNFOLLOW USER
    @Test
    void unfollow_ValidUsers_SuccessfullyUnfollows() {
        // ARRANGE
        int userId = 1;
        int userIdToUnfollow = 2;

        User user = new User();
        user.setId(userId);
        user.setFollowed(new ArrayList<>(List.of(userIdToUnfollow)));

        User userToUnfollow = new User();
        userToUnfollow.setId(userIdToUnfollow);
        userToUnfollow.setFollowers(new ArrayList<>(List.of(userId)));

        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findById(userIdToUnfollow)).thenReturn(userToUnfollow);

        //ACT
        MessageDto response = userService.unfollowUser(userId, userIdToUnfollow);

        //ASSERT
        assertEquals("El usuario se dejo de seguir exitosamente", response.getMessage());
        assertFalse(user.getFollowed().contains(userIdToUnfollow));
        assertFalse(userToUnfollow.getFollowers().contains(userId));
    }

    @Test
    void unfollow_NotExistentUser_ThrowsNotFoundEx() {

        int userId = 1;
        int userIdToUnfollow = 23232;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findById(userIdToUnfollow)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.unfollowUser(userId, userIdToUnfollow));

    }

    //endregion




}
