package com.gyeong48.rrs.controller;

import com.gyeong48.rrs.request.CreateReviewRequest;
import com.gyeong48.rrs.response.RestaurantReviewResponse;
import com.gyeong48.rrs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public RestaurantReviewResponse getReviews(@PathVariable Long restaurantId, @RequestParam Integer offset, @RequestParam Integer limit) {
        return reviewService.getList(restaurantId, PageRequest.of(offset / limit, limit));
    }
}
