package com.api.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable{
    private int userId;
    private String name;
    private List<User> followed;
    private List<User> followers;
    private List<Post> posts;

    @Override
    public int getId() {
        return this.userId;
    }
}
