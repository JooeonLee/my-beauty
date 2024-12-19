package me.jooeon.mybeauty.domain.article.infrastructure;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.repository.ArticleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepositoryImpl extends JpaRepository<Article, Long>, ArticleRepository {
}
