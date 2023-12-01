package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurantId(Long restaurantId);

    //개인 피드백 -> 네이밍 메서드로 만든 deleteAllByRestaurantId(Long restaurantId)를 사용하면
    //          -> 그 내부 동작이 for(T entity : findAllByRestaurantId(Long restaurantId)) {
    //                              delete(entity);
    //                          }
    //          -> 처럼 동작할 것이다(대략적인 코드이다.)
    //          -> 즉 delete 문이 튜플에 개수에 따라 만들어지게 되므로 여러 delete 문이 생기게 된다.
    //          -> 그렇기 때문에 bulk 연산을 위해 @Modifying, @Query 애노테이션을 활용하여 사용자 정의 쿼리를 활용하자.
    //          -> 단 bulk 연산 수행시 영속성 컨텍스트를 무시하고 직접 db에 쿼리를 보내기 때문에
    //          -> 영속성 컨텍스트가 비워있거나 그렇지 않으면 비우고 벌크 연산을 수행해야 이상 현상을 피할 수 있다.
    @Modifying
    @Query("delete from Menu m where m.restaurant.id = :restaurantId")
    void bulkDeleteByRestaurantId(@Param("restaurantId") Long restaurantId);
}
