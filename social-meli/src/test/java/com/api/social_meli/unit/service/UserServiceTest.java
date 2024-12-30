package com.api.social_meli.unit.service;
import com.api.social_meli.dto.GetFollowerCountDto;
import com.api.social_meli.dto.MessageDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.impl.UserRepositoryImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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
