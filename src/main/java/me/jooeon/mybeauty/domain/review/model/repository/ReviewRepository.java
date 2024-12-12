package me.jooeon.mybeauty.domain.review.model.repository;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ReviewRepository {

    public long countByMemberId(Long memberId);

    public Optional<Review> findById(Long id);

    public <S extends Review> S save(S entity);

    Slice<Review> findByCosmeticIdOrderByCreatedAtDesc(Long cosmeticId, Pageable pageable);
    Slice<Object[]> findByCosmeticIdOrderByCreatedAtDesc2(Long cosmeticId, Status status, Pageable pageable);

    Slice<Review> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
    Slice<Object[]> findByMemberIdOrderByCreatedAtDesc2(Long memberId, Status status, Pageable pageable);

    Slice<Review> findByMemberIdAndStatusOrderByCreatedAtDesc(Long memberId, Status status, Pageable pageable);

    Slice<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<Object[]> findAllByOrderByCreatedAtDesc2(Status status, Pageable pageable);

    public void deleteAllInBatch();

    ReviewStatisticsDto findStatisticsByCosmeticId(Long cosmeticId);
}
