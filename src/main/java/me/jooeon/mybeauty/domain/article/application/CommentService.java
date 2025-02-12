package me.jooeon.mybeauty.domain.article.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.article.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.CommentRepository;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public long createComment(CommentSaveRequestDto requestDto, long memberId, Article article) {

        return 100L;
    }
}
