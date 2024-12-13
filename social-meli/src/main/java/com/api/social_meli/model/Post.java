package com.api.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Identifiable{
    private int postId;
    private User seller;
    private LocalDate publishDate;
    private Product product;
    private int categoryId;
    private double price;
    private boolean hasPromo;
    private double discount;

    @Override
    public Long getId() {
        return null;
    }
}
