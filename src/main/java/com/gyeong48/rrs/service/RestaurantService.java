package com.gyeong48.rrs.service;

import com.gyeong48.rrs.domain.Menu;
import com.gyeong48.rrs.domain.Restaurant;
import com.gyeong48.rrs.repository.MenuRepository;
import com.gyeong48.rrs.repository.RestaurantRepository;
import com.gyeong48.rrs.request.CreateAndEditRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void create(CreateAndEditRestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        restaurantRepository.save(restaurant);

        request.getMenus().forEach((menuRequest) -> {
                    Menu menu = Menu.builder()
                            .name(menuRequest.getName())
                            .price(menuRequest.getPrice())
                            .createdAt(ZonedDateTime.now())
                            .updatedAt(ZonedDateTime.now())
                            .restaurant(restaurant)
                            .build();

                    menuRepository.save(menu);
                }
        );
    }
}
