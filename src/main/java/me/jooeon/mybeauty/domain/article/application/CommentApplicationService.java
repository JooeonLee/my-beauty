package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.mapper.CommentMapper;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public List<CommentReadResponseDto> getCommentByArticleId(long articleId) {

        Article article = articleService.getArticleById(articleId);

        List<Comment> commentList = commentService.findCommentByArticleId(article.getId());

        Set<Long> memberIdList = commentList.stream()
                .map(Comment::getMemberId)
                .collect(Collectors.toSet());

        Map<Long, MemberSimpleProfileDto> memberInfoMap = memberPort.getExternalMembersByIds(memberIdList).stream()
                .collect(Collectors.toMap(ExternalMemberDto::getMemberId, MemberSimpleProfileDto::fromExternal));

        return commentList.stream()
                .map(comment -> CommentMapper.toCommentReadResponseDto(comment, memberInfoMap.get(comment.getMemberId())))
                .toList();
    }
}
