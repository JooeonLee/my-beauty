package me.jooeon.mybeauty.domain.cosmetic.infrastructure;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CosmeticRepositoryImpl extends JpaRepository<Cosmetic, Long>, CosmeticRepository {

    @Query("select c, b, AVG(r.star), COUNT(r.id) " +
            "from Cosmetic c " +
            "left join c.brand b " +
            "left join Review r on r.cosmetic.id = c.id " +
            "where c.category.id = :categoryId " +
            "group by c.id, b.id " +
            "order by AVG(r.star) desc ")
    Slice<Object[]> findWithReviewStatsAndBrandByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

}
