package com.gyeong48.rrs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RestaurantDetailResponse {

    private final Long id;
    private final String name;
    private final String address;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final List<RestaurantDetailResponseMenu> menus;
}
