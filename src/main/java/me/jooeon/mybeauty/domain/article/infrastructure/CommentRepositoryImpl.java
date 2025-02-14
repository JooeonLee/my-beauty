package me.jooeon.mybeauty.domain.article.infrastructure;

import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryImpl extends JpaRepository<Comment, Long>, CommentRepository {
}
