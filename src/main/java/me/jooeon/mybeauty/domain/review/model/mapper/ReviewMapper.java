package me.jooeon.mybeauty.domain.review.model.mapper;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSimpleInfoDto;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewResponseDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewWithCosmeticResponseDto;
import me.jooeon.mybeauty.global.common.util.DateUtil;

import java.time.LocalDate;

public class ReviewMapper {

    public static ReviewResponseDto toReviewResponseDto(Review review, long likeCount) {

        Member member = review.getMember();
        MemberSimpleProfileDto memberSimpleProfileDto = MemberSimpleProfileDto.from(member);

        String daysAgo = DateUtil.formatElapsedTime(review.getCreatedAt().toLocalDate());

        return ReviewResponseDto.builder()
                .memberProfile(memberSimpleProfileDto)
                .daysAgo(daysAgo)
                .reviewId(review.getId())
                .star(review.getStar())
                .oneLineReview(review.getOneLineReview())
                .reviewComment(review.getContent())
                .likeCount(likeCount)
                .build();
    }

    public static ReviewWithCosmeticResponseDto toReviewWithCosmeticResponseDto(Review review, long likeCount) {
        ReviewResponseDto reviewResponseDto = toReviewResponseDto(review, likeCount);

        Cosmetic cosmetic = review.getCosmetic();
        CosmeticSimpleInfoDto cosmeticSimpleInfoDto = CosmeticSimpleInfoDto.from(cosmetic);

        return ReviewWithCosmeticResponseDto.builder()
                .cosmeticInfo(cosmeticSimpleInfoDto)
                .reviewInfo(reviewResponseDto)
                .build();

    }
}
