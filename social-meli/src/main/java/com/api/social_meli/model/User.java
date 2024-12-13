package com.api.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable{
    private long userId;
    private String name;
    private List<User> followed;
    private List<User> followers;
    private List<Post> posts;
    //private boolean isSeller; //ToDo: usar un metodo isSeller(bool devolver) en ves de usar un atributo

    @Override
    public Long getId() {
        return null;
    }
}
