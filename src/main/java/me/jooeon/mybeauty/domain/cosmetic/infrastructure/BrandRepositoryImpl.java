package me.jooeon.mybeauty.domain.cosmetic.infrastructure;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.BrandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepositoryImpl extends JpaRepository<Brand, Long>, BrandRepository {
}
