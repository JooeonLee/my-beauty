package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ReviewLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.review.application.ReviewService;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikesService {

    private final ReviewLikesRepository reviewLikesRepository;
    private final MemberPort memberPort;
    private final ReviewService reviewService;
    private final GenericLikesService genericLikesService;

    @Transactional
    public boolean toggleLikes(Long memberId, Long reviewId) {
        return genericLikesService.toggleLikes(
                memberId,
                reviewId,
                memberPort::getMemberById,
                reviewService::getReviewById,
                reviewLikesRepository::findByMemberIdAndReviewId,
                (member, review) -> ReviewLikes.builder()
                        .member(member)
                        .review(review)
                        .status(Status.ACTIVE)
                        .build(),
                reviewLikesRepository::save
        );
    }
}
