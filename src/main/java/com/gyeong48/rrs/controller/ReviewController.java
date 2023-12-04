package com.gyeong48.rrs.controller;

import com.gyeong48.rrs.request.CreateReviewRequest;
import com.gyeong48.rrs.response.RestaurantReviewResponse;
import com.gyeong48.rrs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public void createReview(@RequestBody CreateReviewRequest request) {
        reviewService.create(request);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
    }

    @GetMapping("/restaurants/{restaurantId}/review")
    public RestaurantReviewResponse getReviews(@PathVariable Long restaurantId) {
        return reviewService.getList(restaurantId);
    }
}
