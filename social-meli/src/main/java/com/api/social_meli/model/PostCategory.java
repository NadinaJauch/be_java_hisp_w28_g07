package com.api.social_meli.model;

public class PostCategory implements Identifiable{
    private int postCategoryId;
    private String description;

    @Override
    public int getId() {
        return this.postCategoryId;
    }
}
