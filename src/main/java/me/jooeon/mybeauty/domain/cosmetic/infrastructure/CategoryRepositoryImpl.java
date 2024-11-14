package me.jooeon.mybeauty.domain.cosmetic.infrastructure;

import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryImpl extends JpaRepository<Category, Long>, CategoryRepository {
}
