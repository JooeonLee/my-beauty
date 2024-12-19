package me.jooeon.mybeauty.domain.article.model.repository;

import me.jooeon.mybeauty.domain.article.model.Article;

public interface ArticleRepository {

    public <S extends Article> S save(S article);

}
