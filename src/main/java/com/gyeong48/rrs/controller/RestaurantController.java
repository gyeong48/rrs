package com.gyeong48.rrs.controller;

import com.gyeong48.rrs.request.CreateAndEditRestaurantRequest;
import com.gyeong48.rrs.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String getRestaurants() {
        return "This is getRestaurants API";
    }

    @GetMapping("/restaurants/{restaurantId}")
    public String getRestaurant(@PathVariable Long restaurantId) {
        return "This is getRestaurant API and PathVariable is " + restaurantId;
    }

    @PostMapping("/restaurants")
    public String createRestaurant(@RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.create(request);
        return "This is createRestaurants API"
                + " restaurant name=" + request.getName()
                + " restaurant address=" + request.getAddress()
                + " menus[0].name=" + request.getMenus().get(0).getName()
                + " menus[0].price=" + request.getMenus().get(0).getPrice();
    }

    @PutMapping("/restaurants/{restaurantId}")
    public String editRestaurant(@PathVariable Long restaurantId, @RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.edit(restaurantId, request);
        return "This is editRestaurant API and PathVariable is " + restaurantId
                + " restaurant name=" + request.getName()
                + " restaurant address=" + request.getAddress()
                + " menus[0].name=" + request.getMenus().get(0).getName()
                + " menus[0].price=" + request.getMenus().get(0).getPrice();
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public String deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.delete(restaurantId);
        return "This is deleteRestaurant API and PathVariable is " + restaurantId;
    }
}
