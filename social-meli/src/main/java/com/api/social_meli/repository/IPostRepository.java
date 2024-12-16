package com.api.social_meli.repository;

import com.api.social_meli.model.Post;

import java.util.List;

public interface IPostRepository {
    public List<Post> findAll();
    public Post findById(int id);

    public Post create(Post post);
}
