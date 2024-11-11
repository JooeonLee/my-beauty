package me.jooeon.mybeauty.domain.review.model.repository;

import me.jooeon.mybeauty.domain.review.model.Review;

public interface ReviewRepository {

    public long countByMemberId(Long memberId);

    public <S extends Review> S save(S entity);
}
