package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
