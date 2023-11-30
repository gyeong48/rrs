package com.gyeong48.rrs.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateAndEditRestaurantRequest {
    private String name;
    private String address;
    private List<CreateAndEditRestaurantRequestMenu> menus;
}
