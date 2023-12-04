package com.gyeong48.rrs.request;

import lombok.Getter;

@Getter
public class CreateReviewRequest {
    private Long restaurantId;
    private String content;
    private double score;
}
