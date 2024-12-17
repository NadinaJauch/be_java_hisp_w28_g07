package com.api.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetFollowedsByUserIdDto {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String name;
}
