package com.gyeong48.rrs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantDetailResponseMenu {

    private final Long id;
    private final String name;
    private final int price;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
}
