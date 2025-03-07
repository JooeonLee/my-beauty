package me.jooeon.mybeauty.domain.article.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.CommentRepository;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public long createComment(CommentSaveRequestDto requestDto, long memberId, Article article) {

        Comment comment = Comment.builder()
                .memberId(memberId)
                .article(article)
                .content(requestDto.getContent())
                .status(Status.ACTIVE)
                .build();

        // todo 검증 코드 추가
        // validate(comment);

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public List<Comment> findCommentByArticleId(long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

}
