package me.jooeon.mybeauty.domain.review.infrastructure;

import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepositoryImpl extends JpaRepository<Review, Long>, ReviewRepository {

    @Query("select count(r) from Review r "
    + "where r.member.id = :memberId ")
    long countByMemberId(@Param("memberId") Long memberId);

}
