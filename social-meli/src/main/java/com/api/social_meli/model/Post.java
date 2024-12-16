package com.api.social_meli.model;

import com.api.social_meli.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Identifiable{
    private int postId;
    private int seller;
    private LocalDate publishDate;
    private Product product;
    private int categoryId;
    private double price;
    private boolean hasPromo;
    private double discount;

    @Override
    public int getId() {
        return this.postId;
    }

    public Post(PostDto dto){
        this.seller = dto.getSeller();
        this.publishDate = dto.getPublishDate();
        this.product = new Product(dto.getProduct()); //ToDo: constructor de product para productDto
        this.categoryId = dto.getCategoryId();
        this.price = dto.getPrice();

    }
}

