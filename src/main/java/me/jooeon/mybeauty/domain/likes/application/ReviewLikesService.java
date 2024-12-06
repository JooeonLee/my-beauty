package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.likes.model.Likes;
import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ReviewLikesRepository;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewLikesService {

    private final ReviewLikesRepository reviewLikesRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public boolean toggleLikes(Long memberId, Long reviewId) {

        // todo 커스텀 예외 처리 필요
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 처리 필요
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(IllegalArgumentException::new);

        Optional<ReviewLikes> reviewLikesOptional = reviewLikesRepository.findByMemberIdAndReviewId(memberId, reviewId);

        if(reviewLikesOptional.isPresent()) {
            ReviewLikes reviewLikes = reviewLikesOptional.get();

            if(reviewLikes.getStatus() == Status.DELETED) {
                reviewLikes.restore();
                return true;
            }
            else {
                reviewLikes.softDelete();
                return false;
            }
        }

        ReviewLikes reviewLikes = ReviewLikes.builder()
                .member(member)
                .review(review)
                .status(Status.ACTIVE)
                .build();
        reviewLikesRepository.save(reviewLikes);
        return true;
    }
}
