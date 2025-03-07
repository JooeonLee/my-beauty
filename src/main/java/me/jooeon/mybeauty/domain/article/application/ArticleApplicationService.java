package me.jooeon.mybeauty.domain.article.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleApplicationService {

    private final ArticleService articleService;
    private final MemberPort memberPort;
    private final ImageService imageService;
    private final CommentApplicationService commentApplicationService;
    private final CommentService commentService;

    public long createArticle(ArticleSaveRequestDto requestDto, long memberId, MultipartFile articleImage) {
        Member member = memberPort.getMemberById(memberId);

        String articleImageUrl = null;
        if (!articleImage.isEmpty()) {
            articleImageUrl = imageService.upload(articleImage, ImagePrefix.ARTICLE);
        }

        return articleService.createArticle(requestDto.getTitle(), requestDto.getContent(), member.getId(), articleImageUrl);

    }

    public ArticleReadResponseDto getArticleById(long articleId) {
        Article article = articleService.getArticleById(articleId);
        ExternalMemberDto externalMemberDto = memberPort.getExternalMemberById(article.getMemberId());
        MemberSimpleProfileDto authorInfo = MemberSimpleProfileDto.fromExternal(externalMemberDto);
        List<CommentReadResponseDto> commentReadResponseDtoList = commentApplicationService.getCommentByArticleId(articleId);
        long commentCount = commentService.countCommentByArticleId(articleId);
        // todo : 실제 카운트 된 좋아요 수로 조회하기
        long likeCount = 0L;

        return ArticleReadResponseDto.of(article, authorInfo, commentCount, likeCount, commentReadResponseDtoList);

    }
}
