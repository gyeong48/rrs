package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
