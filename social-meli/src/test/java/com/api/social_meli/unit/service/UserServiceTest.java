package com.api.social_meli.unit.service;
import com.api.social_meli.dto.*;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.service.impl.UserServiceImpl;
import com.api.social_meli.util.MockFactoryUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IUserRepository userRepository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    UserServiceImpl userService;

    //region GET FOLLOWS ORDER ERROR
    @Test
    @DisplayName("Obtener Followeds orden inválido.")
    public void getFollowedsOrderedByNameInvalidOrderThrowException(){
        //Arrange
        String order = "Ordenar A-Z";
        User user = MockFactoryUtils.getUserWithFolloweds();

        //Act
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(user);
        Mockito.when(userRepository.exists(user.getUserId())).thenReturn(true);

        //Assert
        Assertions.assertThrows(BadRequestException.class, ()->userService.getFollowedsOrderedByName(user.getUserId(), order));
    }

    @Test
    @DisplayName("Obtener Followeds orden válido.")
    public void getFollowedsOrderedByNameValidOrderThrowException(){
        //Arrange
        String order = "name_asc";
        User user = MockFactoryUtils.getUserWithFolloweds();

        //Act
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(user);
        Mockito.when(userRepository.exists(user.getUserId())).thenReturn(true);
        Mockito.when(mapper.convertValue(userRepository.findById(2), FollowDto.class)).thenReturn(new FollowDto(2,""));
        FollowedListDto result = userService.getFollowedsOrderedByName(user.getUserId(), order);

        //Assert
        Assertions.assertEquals(3, result.getFollowed().size());
    }

    @Test
    @DisplayName("Obtener Followers orden inválido.")
    public void getFollowersOrderedByNameInvalidOrderThrowException(){
        //Arrange
        String order = "Ordenar Z-A";
        User user = MockFactoryUtils.getUserWithFollowersAndPost();

        //Act
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(user);
        Mockito.when(userRepository.exists(user.getUserId())).thenReturn(true);

        //Assert
        Assertions.assertThrows(BadRequestException.class, ()->userService.getFollowersOrderedByName(user.getUserId(), order));
    }

    @Test
    @DisplayName("Obtener Followers orden válido.")
    public void getFollowersOrderedByNameValidOrderThrowException(){
        //Arrange
        String order = "name_desc";
        User user = MockFactoryUtils.getUserWithFollowersAndPost();

        //Act
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(user);
        Mockito.when(userRepository.exists(user.getUserId())).thenReturn(true);
        Mockito.when(mapper.convertValue(userRepository.findById(1), FollowDto.class)).thenReturn(new FollowDto(1,""));
        FollowerListDto result = userService.getFollowersOrderedByName(user.getUserId(), order);

        //Assert
        Assertions.assertEquals(3, result.getFollowers().size());
    }
    //endregion

    //region UNFOLLOW USER
    @Test
    void unfollowValidUsersSuccessfullyUnfollows() {
        // ARRANGE
        int userId = 1;
        int userIdToUnfollow = 2;
        User user = MockFactoryUtils.createUserWithOnlyFollowersAndFolloweds(userId, new ArrayList<>(List.of(userIdToUnfollow)), new ArrayList<>());
        User userToUnfollow = MockFactoryUtils.createUserWithOnlyFollowersAndFolloweds(userIdToUnfollow, new ArrayList<>(),  new ArrayList<>(List.of(userId)));
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
    void unfollowNotExistentUserThrowsNotFoundEx() {
        //ARRANGE
        int userId = 1;
        int userIdToUnfollow = 23232;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findById(userIdToUnfollow)).thenReturn(null);

        //ACT & ASSERT
        assertThrows(NotFoundException.class, () -> userService.unfollowUser(userId, userIdToUnfollow));
    }
    //endregion

    //region VERIFY FOLLOWER COUNT

    @Test
    void getFollowerCountValidUserReturnsCorrectCount(){
        //ARRANGE
        User user = new User();
        user.setId(1);
        user.setFollowers(new ArrayList<>(List.of(2,3,4)));
        when(userRepository.findById(1)).thenReturn(user);

        //ACT
        GetFollowerCountDto getFollowerCountDto = userService.getFollowerCount(1);

        //ASSERT
        assertEquals(3,getFollowerCountDto.getFollowers_count());
    }
    //endregion

    //region FOLLOW USER
    @Test
    @DisplayName("Intentar seguir a usuario existente")
    void shouldFollowUserSuccessfullyWhenUserExists(){
        // ARRANGE

        int userId = 2;
        int userIdToFollow = 3;
        User userFollower = MockFactoryUtils.createUserWithIdAndFollowed(userId);
        User userToFollow = MockFactoryUtils.createUserWithFollowersAndPost(userIdToFollow);

        when(userRepository.findById(userId)).thenReturn(userFollower);
        when(userRepository.findById(userIdToFollow)).thenReturn(userToFollow);

        //ACT
        MessageDto response = userService.followUser(userId, userIdToFollow);

        //ASSERT
        assertEquals("El usuario se comenzo a seguir exitosamente", response.getMessage());
        assertTrue(userFollower.getFollowed().contains(userIdToFollow), "El usuario debería estar en la lista de seguidos.");
        assertTrue(userToFollow.getFollowers().contains(userId), "El usuario seguidor debería estar en la lista de seguidores.");
    }

    @Test
    @DisplayName("Intentar seguir a usuario no existente")
    void shouldNotFollowUserWhenNonexistentUser(){
        // ARRANGE
        int userId = 2;
        int userIdToFollow = 3000;
        User userFollower = MockFactoryUtils.createUserWithIdAndFollowed(userId);

        when(userRepository.findById(userId)).thenReturn(userFollower);
        when(userRepository.findById(userIdToFollow)).thenReturn(null);

        // ACT & ASSERT
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            userService.followUser(userId, userIdToFollow);
        });

        assertEquals("Usuario o vendedor no encontrado", thrown.getMessage());
    }
    //endregion

}
