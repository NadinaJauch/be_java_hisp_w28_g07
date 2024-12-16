package com.api.social_meli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private int userId;
    private int postId;
    private LocalDate date;
    private ProductDto product;
}
