package com.gyeong48.rrs.controller;

import com.gyeong48.rrs.domain.Restaurant;
import com.gyeong48.rrs.request.CreateAndEditRestaurantRequest;
import com.gyeong48.rrs.response.GetRestaurantResponse;
import com.gyeong48.rrs.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {
        return restaurantService.getList();
    }

    @GetMapping("/restaurants/{restaurantId}")
    public GetRestaurantResponse getRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.get(restaurantId);
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
