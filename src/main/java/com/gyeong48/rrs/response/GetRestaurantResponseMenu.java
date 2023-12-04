package com.gyeong48.rrs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class GetRestaurantResponseMenu {

    private Long id;
    private String name;
    private int price;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
