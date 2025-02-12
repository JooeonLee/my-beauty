package me.jooeon.mybeauty.domain.article.model.repository;

import me.jooeon.mybeauty.domain.article.model.Article;

import java.util.Optional;

public interface ArticleRepository {

    public <S extends Article> S save(S article);

    public Optional<Article> findById(Long id);

}
