package com.api.social_meli.repository;

import com.api.social_meli.model.User;
import java.util.List;

public interface IUserRepository {
    List<User> findAll();
    User findById(int id);
    boolean exists(int id);
    User create(User user);
}
