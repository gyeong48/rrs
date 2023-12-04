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

    public List<Restaurant> getList() {
        return restaurantRepository.findAll();
    }

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
        //피드백 -> RuntimeException 예외를 던져주고 메시지를 만들어 의미를 조금 더 명확하게 한다.
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("없는 맛집 입니다."));
        //개인 피드백 -> 강의 에서는 save()를 사용했지만 나는 변경 감지를 이용했다.
        restaurant.changeNameAndAddress(request.getName(), request.getAddress());

        //피드백 -> HTTP 메서드 중 PUT 메서드의 의미는 전체 수정이다.
        //      -> 그러므로 전체 삭제를 한뒤에 다시 값을 DB에 넣어준다.
        //      -> 부분 수정을 원하면 PATCH 메서드를 사용해야 그 의미가 통한다.
        //      -> HTTP 메서드를 PUT, PATCH를 의도와 다르게 사용하면 에러가 발생하는 것이 아니다.
        //      -> 다만 우리는 약속을 했기 때문에 그 규약에 맞게 기능을 논리적으로 설계 해야 하기 때문이다.
        //      -> PATCH 메서드 처럼 사용하려면 바로 이전 커밋 코드처럼 사용하면 된다.
        //
        //      -> 아래 주석 처리된 코드는 벌크연산을 적용하기 이전 코드이다 벌크 연산을 사용하려면 bulkDeleteByRestaurantId()를 사용하자
        //List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        //menuRepository.deleteAll(menus);
        menuRepository.bulkDeleteByRestaurantId(restaurantId);
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
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        menuRepository.bulkDeleteByRestaurantId(restaurantId);
    }
}
