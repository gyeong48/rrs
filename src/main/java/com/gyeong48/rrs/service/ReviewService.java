package com.gyeong48.rrs.service;

import com.gyeong48.rrs.domain.Restaurant;
import com.gyeong48.rrs.domain.Review;
import com.gyeong48.rrs.repository.RestaurantRepository;
import com.gyeong48.rrs.repository.ReviewRepository;
import com.gyeong48.rrs.request.CreateReviewRequest;
import com.gyeong48.rrs.response.RestaurantReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

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
        //피드백 -> 유효성 검사를 명시해주는 것이 좋은 것 같다.
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("존재 하지 않는 리뷰입니다."));
        reviewRepository.delete(review);
    }

    public RestaurantReviewResponse getList(Long restaurantId) {
        restaurantRepository.findById(restaurantId).orElseThrow(); //유효성 검사
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);
        double avgScore = 0.0;

        for (Review review : reviews) avgScore += review.getScore();
        if (!reviews.isEmpty()) avgScore = avgScore / reviews.size();

        return RestaurantReviewResponse.builder()
                .avgScore(avgScore)
                .reviews(reviews.stream().map(
                        review -> RestaurantReviewResponse.Review.builder()
                                .id(review.getId())
                                .content(review.getContent())
                                .score(review.getScore())
                                .createdAt(review.getCreatedAt())
                                .build()
                ).toList())
                .build();
    }
}
