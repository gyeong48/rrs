package com.gyeong48.rrs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantReviewResponse {

    private final double avgScore;
    private final List<Review> reviews;
    private final RestaurantReviewResponsePage page;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Review {
        private Long id;
        private String content;
        private double score;
        private ZonedDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RestaurantReviewResponsePage {
        private int offset;
        private int limit;
    }
}
