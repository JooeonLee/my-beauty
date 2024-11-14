package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;

import java.util.Optional;

public interface CosmeticRepository {

    public <S extends Cosmetic> S save(S cosmetic);

    public Optional<Cosmetic> findById(Long id);

    public void deleteAllInBatch();
}
