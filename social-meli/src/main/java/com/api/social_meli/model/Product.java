package com.api.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product implements Identifiable{
    private int productId;
    private String name;
    private String type;
    private String brand ;
    private String colour;
    private String notes;

    @Override
    public Long getId() {
        return null;
    }
}