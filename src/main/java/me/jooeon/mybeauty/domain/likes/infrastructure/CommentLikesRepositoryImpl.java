package me.jooeon.mybeauty.domain.likes.infrastructure;

import me.jooeon.mybeauty.domain.likes.model.CommentLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.CommentLikesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepositoryImpl extends JpaRepository<CommentLikes, Long>, CommentLikesRepository {
}
