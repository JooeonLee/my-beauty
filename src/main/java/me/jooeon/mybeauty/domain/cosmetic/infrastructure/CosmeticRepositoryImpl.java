package me.jooeon.mybeauty.domain.cosmetic.infrastructure;

import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CosmeticRepositoryImpl extends JpaRepository<Cosmetic, Long>, CosmeticRepository {
}
