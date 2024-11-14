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

        String daysAgo;
        long daysBetween = DateUtil.calculateDaysBetween(review.getCreatedAt().toLocalDate(), LocalDate.now());
        daysAgo = daysBetween + "일전";
        if (daysBetween > 29) {
            long monthsBetween = DateUtil.calculateMonthsBetween(review.getCreatedAt().toLocalDate(), LocalDate.now());
            daysAgo = monthsBetween + "개월전";
            if (monthsBetween > 11) {
                long yearsBetween = DateUtil.calculateYearsBetween(review.getCreatedAt().toLocalDate(), LocalDate.now());
                daysAgo = yearsBetween + "년전";
            }
        }

        return ReviewResponseDto.builder()
                .memberProfile(memberSimpleProfileDto)
                .daysAgo(daysAgo)
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
