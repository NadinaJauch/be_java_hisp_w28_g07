package com.api.social_meli.model;

import com.api.social_meli.dto.PostDto;
import com.api.social_meli.dto.PromoPostDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Identifiable, Serializable {
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("seller")
    private User seller;
    @JsonProperty("publish_date")
    private String publishDate;
    private Product product;
    @JsonProperty("category_id")
    private int categoryId;
    private double price;
    @JsonProperty("has_promo")
    private boolean hasPromo;
    private double discount;

    @Override
    public int getId() {
        return this.postId;
    }

    public Post(PromoPostDto dto){
        this.postId = dto.getPostId();
        this
    }


}
