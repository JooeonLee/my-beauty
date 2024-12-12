package me.jooeon.mybeauty.domain.review.infrastructure;

import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepositoryImpl extends JpaRepository<Review, Long>, ReviewRepository {

    @Query("select count(r) from Review r "
    + "where r.member.id = :memberId ")
    long countByMemberId(@Param("memberId") Long memberId);

    @Query("select new me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto(AVG(r.star), COUNT(r)) " +
            "from Review r " +
            "where r.cosmetic.id = :cosmeticId ")
    ReviewStatisticsDto findStatisticsByCosmeticId(@Param("cosmeticId") Long cosmeticId);

    @Query("select r as review, count(l) as likeCount " +
            "from Review r " +
            "left join ReviewLikes l on l.review.id = r.id and l.status = :status " +
            "where r.member.id = :memberId " +
            "group by r " +
            "order by r.createdAt desc ")
    Slice<Object[]> findByMemberIdOrderByCreatedAtDesc2(@Param("memberId") Long memberId, @Param("status") Status status, Pageable pageable);

    @Query("select r as review, count(l) as likeCount " +
            "from Review  r " +
            "left join ReviewLikes l on l.review.id = r.id and l.status = :status " +
            "group by r " +
            "order by r.createdAt desc ")
    Slice<Object[]> findAllByOrderByCreatedAtDesc2(@Param("status") Status status, Pageable pageable);

    @Query("select r as review, count(l) as likeCount " +
            "from Review r " +
            "left join ReviewLikes l on l.review.id = r.id and l.status = :status " +
            "where r.cosmetic.id = :cosmeticId " +
            "group by r " +
            "order by r.createdAt desc ")
    Slice<Object[]> findByCosmeticIdOrderByCreatedAtDesc2(@Param("cosmeticId") Long cosmeticId, @Param("status") Status status, Pageable pageable);

}
