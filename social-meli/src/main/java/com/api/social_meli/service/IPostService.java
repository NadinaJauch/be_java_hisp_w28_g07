package com.api.social_meli.service;

import com.api.social_meli.dto.PromoPostDto;

public interface IPostService {
    PromoPostDto getPromoProductCount(Integer userId);
}
