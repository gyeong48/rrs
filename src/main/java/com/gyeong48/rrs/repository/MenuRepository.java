package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurant_Id(Long id);
    List<Menu> deleteByRestaurant_Id(Long id);
}
