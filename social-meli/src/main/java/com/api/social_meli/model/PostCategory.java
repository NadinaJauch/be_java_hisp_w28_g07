package com.api.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCategory implements Identifiable{
    private int postCategoryId;
    private String description;

    @Override
    public int getId() {
        return this.postCategoryId;
    }
}
