package com.api.social_meli.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class getFollowerCountDto {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String name;
    private int followers_count;
}
