package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;

import java.util.Optional;

public interface BrandRepository {

    public <S extends Brand> S save(S entity);

    public void deleteAllInBatch();

    public Optional<Brand> findById(Long id);
}
