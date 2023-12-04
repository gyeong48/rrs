package com.gyeong48.rrs.service;

import com.gyeong48.rrs.domain.Restaurant;
import com.gyeong48.rrs.domain.Review;
import com.gyeong48.rrs.repository.RestaurantRepository;
import com.gyeong48.rrs.repository.ReviewRepository;
import com.gyeong48.rrs.request.CreateReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void create(CreateReviewRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("없는 맛집 입니다."));

        Review review = Review.builder()
                .content(request.getContent())
                .score(request.getScore())
                .createdAt(ZonedDateTime.now())
                .restaurant(restaurant)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
