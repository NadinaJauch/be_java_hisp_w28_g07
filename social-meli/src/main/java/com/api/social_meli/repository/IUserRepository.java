package com.api.social_meli.repository;

import com.api.social_meli.model.User;

public interface IUserRepository {
    User create(User user);
    User findById(int id);
}
