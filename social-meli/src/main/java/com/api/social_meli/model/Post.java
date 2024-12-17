package com.api.social_meli.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Identifiable{
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("seller")
    private User seller;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
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

    @JsonProperty("user_id")
    public int getUserId(){
        return seller.getUserId();
    }
}
