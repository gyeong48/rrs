package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {
    Double getAvgScoreByRestaurantId(Long restaurantId);

    Slice<Review> findSliceByRestaurantId(Long restaurantId, Pageable page);
}
