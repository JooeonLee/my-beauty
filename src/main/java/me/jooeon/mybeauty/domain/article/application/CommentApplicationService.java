package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import org.springframework.stereotype.Service;

@Service
public class CommentApplicationService {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberPort memberPort;

    public CommentApplicationService(CommentService commentService, MemberPort memberPort, ArticleService articleService) {
        this.commentService = commentService;
        this.memberPort = memberPort;
        this.articleService = articleService;
    }

    public long createComment(CommentSaveRequestDto requestDto, long articleId, long memberId) {
        ExternalMemberDto externalMemberDto = memberPort.getExternalMemberById(memberId);

        Article article = articleService.getArticleById(articleId);

        return commentService.createComment(requestDto, externalMemberDto.getMemberId(), article);
    }
}
