package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;

public interface BrandRepository {

    public <S extends Brand> S save(S entity);

    public void deleteAllInBatch();
}
