package com.api.social_meli.unit.service;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.impl.UserRepositoryImpl;
import com.api.social_meli.service.impl.UserServiceImpl;
import com.api.social_meli.util.MockFactoryUtils;
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

    private final MockFactoryUtils userFactoryUtils = new MockFactoryUtils();

    //region UNFOLLOW USER
    @Test
    void unfollowValidUsersSuccessfullyUnfollows() {
        // ARRANGE
        int userId = 1;
        int userIdToUnfollow = 2;
        User user = userFactoryUtils.createUserWithOnlyFollowersAndFolloweds(userId, new ArrayList<>(List.of(userIdToUnfollow)), new ArrayList<>());
        User userToUnfollow = userFactoryUtils.createUserWithOnlyFollowersAndFolloweds(userIdToUnfollow, new ArrayList<>(),  new ArrayList<>(List.of(userId)));
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

}
