package me.jooeon.mybeauty.domain.review.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewStatisticsDto {

    private double avgStar;
    private long reviewCount;

    public ReviewStatisticsDto(double avgStar, long reviewCount) {
        this.avgStar = avgStar;
        this.reviewCount = reviewCount;
    }
}
