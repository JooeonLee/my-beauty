package me.jooeon.mybeauty.domain.cosmetic.model.repository;

import me.jooeon.mybeauty.domain.cosmetic.model.Category;

public interface CategoryRepository {

    public <S extends Category> S save(S entity);

    public void deleteAllInBatch();
}
