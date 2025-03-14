package me.jooeon.mybeauty.domain.article.model.repository;

import me.jooeon.mybeauty.domain.article.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    public <S extends Comment> S save(S comment);

    public List<Comment> findByArticleId(long articleId);

    public long countByArticleId(long articleId);

    public Optional<Comment> findById(long commentId);
}
