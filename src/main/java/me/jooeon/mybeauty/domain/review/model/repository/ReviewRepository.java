package me.jooeon.mybeauty.domain.review.model.repository;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.review.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ReviewRepository {

    public long countByMemberId(Long memberId);

    public Optional<Review> findById(Long id);

    public <S extends Review> S save(S entity);

    Slice<Review> findByCosmeticIdOrderByCreatedAtDesc(Long cosmeticId, Pageable pageable);

    Slice<Review> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    Slice<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

    public void deleteAllInBatch();
}
