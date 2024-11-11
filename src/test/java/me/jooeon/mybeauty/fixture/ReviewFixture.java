package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.global.common.model.enums.Status;

public class ReviewFixture {

    public static Review 리뷰(Member member, Cosmetic cosmetic) {

        return 리뷰(member, cosmetic, 5, "테스트 리뷰 내용", "테스트 리뷰 한줄평");
    }

    public static Review 리뷰(Member member, Cosmetic cosmetic, int star, String content, String oneLineReview) {

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
