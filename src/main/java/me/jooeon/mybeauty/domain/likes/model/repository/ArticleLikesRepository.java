package me.jooeon.mybeauty.domain.likes.model.repository;

import me.jooeon.mybeauty.domain.likes.model.ArticleLikes;

import java.util.Optional;

public interface ArticleLikesRepository {

    public <S extends ArticleLikes> S save(S articleLikes);

    public Optional<ArticleLikes> findByMemberIdAndArticleId(Long memberId, Long articleId);
}
