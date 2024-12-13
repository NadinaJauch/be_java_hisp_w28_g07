package com.api.social_meli.repository;

import com.api.social_meli.model.User;

public interface IUserRepository {
    User findById(int id);
}
