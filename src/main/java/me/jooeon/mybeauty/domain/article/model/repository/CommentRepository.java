package me.jooeon.mybeauty.domain.article.model.repository;

import me.jooeon.mybeauty.domain.article.model.Comment;

public interface CommentRepository {

    public <S extends Comment> S save(S comment);
}
