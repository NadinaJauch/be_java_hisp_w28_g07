package com.api.social_meli.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class getFollowerCountDto {
    private int user_id;
    private String user_name;
    private int followers_count;

}
