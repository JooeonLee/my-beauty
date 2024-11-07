package me.jooeon.mybeauty.domain.review.model.repository;

public interface ReviewRepository {

    public long countByMemberId(Long memberId);
}
