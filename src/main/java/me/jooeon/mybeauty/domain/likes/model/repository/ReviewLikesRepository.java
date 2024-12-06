package me.jooeon.mybeauty.domain.likes.model.repository;

import me.jooeon.mybeauty.domain.likes.model.Likes;
import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;

import java.util.Optional;

public interface ReviewLikesRepository {

    public <S extends ReviewLikes> S save(S like);

    public Optional<ReviewLikes> findByMemberIdAndReviewId(Long memberId, Long reviewId);
}
