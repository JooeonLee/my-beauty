package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CosmeticRepository {

    public <S extends Cosmetic> S save(S cosmetic);

    public Optional<Cosmetic> findById(Long id);

    public void deleteAllInBatch();

    Slice<Object[]> findWithReviewStatsAndBrandByCategoryId(Long categoryId, Pageable pageable);
}
