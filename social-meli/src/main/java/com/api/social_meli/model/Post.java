package com.api.social_meli.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Identifiable{
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("seller")
    private User user;
    @JsonFormat(pattern = "yyyy-MM-dd")
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

//    public LocalDate getPublishDate(){
//        return LocalDate.parse(this.publishDate);
//    }
}
