package me.jooeon.mybeauty.domain.likes.infrastructure;

import me.jooeon.mybeauty.domain.likes.model.ArticleLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ArticleLikesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikesRepositoryImpl extends JpaRepository<ArticleLikes, Long>, ArticleLikesRepository {
}
