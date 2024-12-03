package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;

import java.util.Optional;

public interface CategoryRepository {

    public <S extends Category> S save(S entity);

    public void deleteAllInBatch();

    public Optional<Category> findById(Long id);
}
