package com.gyeong48.rrs.service;

import com.gyeong48.rrs.domain.Restaurant;
import com.gyeong48.rrs.domain.Review;
import com.gyeong48.rrs.repository.RestaurantRepository;
import com.gyeong48.rrs.repository.ReviewRepository;
import com.gyeong48.rrs.request.CreateReviewRequest;
import com.gyeong48.rrs.response.RestaurantReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        //피드백 -> 유효성 검사를 명시해주는 것이 좋은 것 같다.
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("존재 하지 않는 리뷰입니다."));
        reviewRepository.delete(review);
    }

    //피드백 -> QueryDSL을 적용한 코드
    public RestaurantReviewResponse getList(Long restaurantId, Pageable page) {
        Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId);
        Slice<Review> reviews = reviewRepository.findSliceByRestaurantId(restaurantId, page);

        return RestaurantReviewResponse.builder()
                .avgScore(avgScore)
                .reviews(reviews.getContent().stream()
                        .map(review -> RestaurantReviewResponse.Review.builder()
                                .id(review.getId())
                                .content(review.getContent())
                                .score(review.getScore())
                                .createdAt(review.getCreatedAt())
                                .build()
                        ).toList()
                )
                .page(RestaurantReviewResponse.RestaurantReviewResponsePage.builder()
                        .offset(page.getPageNumber() * page.getPageSize())
                        .limit(page.getPageSize())
                        .build()
                )
                .build();
    }
}
