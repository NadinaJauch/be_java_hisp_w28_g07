package com.api.social_meli.repository;

import com.api.social_meli.model.User;

import java.util.List;

public interface IUserRepository {
    User create(User user);
    User findById(int id);
    List<Integer> getFollowedsByUserId(int userId);
}
