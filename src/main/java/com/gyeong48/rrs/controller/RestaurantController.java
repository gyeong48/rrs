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
    public void createRestaurant(@RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.create(request);
    }

    @PutMapping("/restaurants/{restaurantId}")
    public void editRestaurant(@PathVariable Long restaurantId, @RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.edit(restaurantId, request);
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public void deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.delete(restaurantId);
    }
}
