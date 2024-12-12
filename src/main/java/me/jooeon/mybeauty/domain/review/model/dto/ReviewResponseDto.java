package me.jooeon.mybeauty.domain.review.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
    private MemberSimpleProfileDto memberProfile;
    private String daysAgo;
    private long reviewId;
    private int star;
    private String oneLineReview;
    private String reviewComment;
    private long likeCount;

    @Builder
    public ReviewResponseDto(MemberSimpleProfileDto memberProfile, String daysAgo, long reviewId, int star, String oneLineReview, String reviewComment, long likeCount) {
        this.memberProfile = memberProfile;
        this.daysAgo = daysAgo;
        this.reviewId = reviewId;
        this.star = star;
        this.oneLineReview = oneLineReview;
        this.reviewComment = reviewComment;
        this.likeCount = likeCount;
    }
}
