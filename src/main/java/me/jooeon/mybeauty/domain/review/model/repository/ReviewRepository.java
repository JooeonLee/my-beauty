package me.jooeon.mybeauty.domain.review.model.repository;

import me.jooeon.mybeauty.domain.review.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepository {

    public long countByMemberId(Long memberId);

    public <S extends Review> S save(S entity);

    Slice<Review> findByCosmeticIdOrderByCreatedAtDesc(Long cosmeticId, Pageable pageable);

    Slice<Review> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    public void deleteAllInBatch();
}
