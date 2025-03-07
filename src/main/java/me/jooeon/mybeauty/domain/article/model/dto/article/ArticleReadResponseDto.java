package me.jooeon.mybeauty.domain.article.model.dto.article;

import lombok.Builder;
import lombok.Getter;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;

import java.util.List;

@Getter
public class ArticleReadResponseDto {

    private final String title;
    private final String content;
    private final String articleImageUrl;
    private final long articleId;
    private final MemberSimpleProfileDto authorInfo;
    private final long commentCount;
    private final long likeCount;
    private final List<CommentReadResponseDto> comments;

    @Builder
    public ArticleReadResponseDto(String title, String content, String articleImageUrl, long articleId, MemberSimpleProfileDto authorInfo, long commentCount, long likeCount, List<CommentReadResponseDto> comments) {
        this.title = title;
        this.content = content;
        this.articleImageUrl = articleImageUrl;
        this.articleId = articleId;
        this.authorInfo = authorInfo;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public static ArticleReadResponseDto of(Article article, MemberSimpleProfileDto authorInfo, long commentCount, long likeCount, List<CommentReadResponseDto> comments) {
        return ArticleReadResponseDto.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorInfo(authorInfo)
                .commentCount(commentCount)
                .likeCount(likeCount)
                .comments(comments)
                .build();
    }
}
