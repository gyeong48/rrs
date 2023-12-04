package com.gyeong48.rrs.repository;

import com.gyeong48.rrs.domain.QReview;
import com.gyeong48.rrs.domain.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Double getAvgScoreByRestaurantId(Long restaurantId) {
        return queryFactory.select(QReview.review.score.avg())
                .from(QReview.review)
                .where(QReview.review.restaurant.id.eq(restaurantId))
                .fetchFirst();
    }

    @Override
    public Slice<Review> findSliceByRestaurantId(Long restaurantId, Pageable page) {
        List<Review> reviews = queryFactory.select(QReview.review)
                .from(QReview.review)
                .where(QReview.review.restaurant.id.eq(restaurantId))
                .offset((long) page.getPageNumber() * page.getPageSize())
                .limit(page.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(
                reviews.stream().limit(page.getPageSize()).toList(),
                page,
                reviews.size() > page.getPageSize()
        );
    }
}
