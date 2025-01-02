package com.api.social_meli.unit.service;

import com.api.social_meli.dto.FollowedSellerPostsDto;
import com.api.social_meli.exception.NotFoundException;
import com.api.social_meli.model.Post;
import com.api.social_meli.model.User;
import com.api.social_meli.repository.IPostCategoryRepository;
import com.api.social_meli.repository.IPostRepository;
import com.api.social_meli.repository.IUserRepository;
import com.api.social_meli.service.impl.PostServiceImpl;
import com.api.social_meli.util.MockFactoryUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    private List<User> usersMock;
    private List<Post> postsMock;

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IPostCategoryRepository postCategoryRepository;

    @Mock
    private IUserRepository userRepository;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    @InjectMocks
    private PostServiceImpl postService;


    @BeforeEach
    public void beforeEach() throws IOException {
        usersMock = MockFactoryUtils.getUsersMock();
        postsMock = MockFactoryUtils.getPostsMock();
    }

    @Test
    @DisplayName("Obtención exclusiva de posts de las últimas dos semanas de los usuarios seguidos por un usuario válido.")
    public void shouldReturnFollowedSellerPostsFromLastTwoWeeksOnly() {
        // ARRANGE
        int userId = 1;
        List<Integer> followedByUser = MockFactoryUtils.getFollowedsByUserId(usersMock, userId);
        MockFactoryUtils.setPostsAsPostedOneWeekAgo(postsMock, List.of(2, 4)); // Se que los posts 2 y 4 son algunos de los posts de usuarios que el usuario 1 sigue

        when(userRepository.findAll()).thenReturn(usersMock);
        when(userRepository.exists(userId)).thenReturn(true);
        followedByUser.forEach(followedUserId ->
                when(postRepository.findByUserId(followedUserId)).thenReturn(MockFactoryUtils.filterPostsByUserId(postsMock, followedUserId))
        );

        // ACT
        FollowedSellerPostsDto result = postService.getFollowedSellersPosts(userId, null);

        // ASSERT
        assertNotNull(result, "El resultado no puede llegar nulo");
        assertNotNull(result.getPosts(), "La lista de posts tiene que existir");
        assertFalse(result.getPosts().isEmpty(), "Tiene que haber posts");
        assertTrue(result.getPosts().stream()
                        .noneMatch(post -> post.getPublishDate().isBefore(LocalDate.now().minusWeeks(2))),
                "Todos los posts deberían tener una fecha de publicación dentro de las últimas dos semanas.");
    }

    @DisplayName("Caso en el que no hay posts (de usuarios seguidos por usuario válido) dentro de las últimas dos semanas")
    public void shouldReturnEmptyListWhenFollowedUsersHaveNoRecentPosts() {
        // ARRANGE
        int userId = 1;
        List<Integer> followedByUser = MockFactoryUtils.getFollowedsByUserId(usersMock, userId);
        MockFactoryUtils.setPostsFromFollowedUsersAsPostedThreeWeeksAgo(postsMock, followedByUser);

        when(userRepository.findAll()).thenReturn(usersMock);
        when(userRepository.exists(userId)).thenReturn(true);
        followedByUser.forEach(followedUserId ->
                when(postRepository.findByUserId(followedUserId)).thenReturn(MockFactoryUtils.filterPostsByUserId(postsMock, followedUserId))
        );

        // ACT
        FollowedSellerPostsDto result = postService.getFollowedSellersPosts(userId, null);

        // ASSERT
        assertNotNull(result, "El resultado no puede llegar nulo");
        assertNotNull(result.getPosts(), "La lista de posts tiene que existir");
        assertTrue(result.getPosts().isEmpty(), "Se esperaba que la lista esté vacía");
    }

    @Test
    @DisplayName("Devuelve excepción con mensaje específico cuando el usuario con ese ID no existe.")
    public void shouldThrowExWhenGetFollowedSellersPostsUserNotExists() {
        // ARRANGE
        int userId = 999;
        when(userRepository.exists(userId)).thenReturn(false);

        // ACT & ASSERT
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            postService.getFollowedSellersPosts(userId, null);
        });
        assertEquals("No se encontró ningún usuario con ese ID.", thrown.getMessage(), "El mensaje de la excepción no es el esperado.");
    }
    // endregion
}
