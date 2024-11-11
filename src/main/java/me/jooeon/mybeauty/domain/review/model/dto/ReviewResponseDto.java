package me.jooeon.mybeauty.domain.review.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
    private MemberSimpleProfileDto memberProfile;
    private String daysAgo;
    private int star;
    private String oneLineReview;
    private String reviewComment;
    private int likeCount;
}
