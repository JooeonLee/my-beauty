package me.jooeon.mybeauty.domain.review.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.global.common.model.enums.Status;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSaveRequestDto {

    private int star;
    private String content;
    private String oneLineReview;

    @Builder
    public ReviewSaveRequestDto(int star, String content, String oneLineReview) {
        this.star = star;
        this.content = content;
        this.oneLineReview = oneLineReview;
    }

    public Review toEntity(Member member, Cosmetic cosmetic) {
        return Review.builder()
                .member(member)
                .cosmetic(cosmetic)
                .star(star)
                .content(content)
                .oneLineReview(oneLineReview)
                .status(Status.ACTIVE)
                .build();
    }
}
