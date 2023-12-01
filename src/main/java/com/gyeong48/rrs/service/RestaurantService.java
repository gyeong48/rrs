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
import java.util.List;

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

    @Transactional
    public void edit(Long restaurantId, CreateAndEditRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow();
        List<Menu> menus = menuRepository.findByRestaurant_Id(restaurantId);

        restaurant.changeNameAndAddress(request.getName(), request.getAddress());
        request.getMenus().forEach(requestMenu -> {
            Menu foundMenu = menus.stream()
                    .filter(menu -> requestMenu.getName().equals(menu.getName()))
                    .findFirst()
                    .orElse(null);

            if (foundMenu != null) {
                foundMenu.changeNameAndPrice(requestMenu.getName(), requestMenu.getPrice());
            } else {
                Menu newMenu = Menu.builder()
                        .name(requestMenu.getName())
                        .price(requestMenu.getPrice())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .restaurant(restaurant)
                        .build();
                menuRepository.save(newMenu);
            }
        });
    }

    @Transactional
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        menuRepository.deleteByRestaurant_Id(restaurantId);
    }
}
