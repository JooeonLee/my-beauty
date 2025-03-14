package me.jooeon.mybeauty.domain.likes.model.repository;

import me.jooeon.mybeauty.domain.likes.model.CommentLikes;

import java.util.Optional;

public interface CommentLikesRepository {

    public <S extends CommentLikes> S save(S like);

    public Optional<CommentLikes> findByMemberIdAndCommentId(Long memberId, Long commentId);
}
