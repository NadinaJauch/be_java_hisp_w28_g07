package com.api.social_meli.unit.service;

import com.api.social_meli.dto.ExceptionDto;
import com.api.social_meli.dto.FollowDto;
import com.api.social_meli.dto.FollowedListDto;
import com.api.social_meli.exception.BadRequestException;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.IUserService;
import com.api.social_meli.service.impl.UserServiceImpl;
import com.api.social_meli.util.MockFactoryUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IUserRepository userRepository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    UserServiceImpl userService;

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
}
