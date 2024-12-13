package com.api.social_meli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private int postId;
    private int userId;
    private LocalDate publishDate;
    private ProductDto product;
    private int categoryId;
    private double price;
    private boolean hasPromo;
    private double discount;
}
