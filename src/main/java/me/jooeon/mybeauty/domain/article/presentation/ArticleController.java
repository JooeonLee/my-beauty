package me.jooeon.mybeauty.domain.article.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.application.ArticleApplicationService;
import me.jooeon.mybeauty.domain.article.application.ArticleService;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleApplicationService articleApplicationService;

    @PostMapping(value = "/articles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse createArticle2(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                      @RequestPart("requestDto") ArticleSaveRequestDto requestDto,
                                      @RequestPart(value = "articleImage", required = false) MultipartFile articleImage) {

        log.info("=== Article Controller createArticle 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        Long createArticleId = articleApplicationService.createArticle(requestDto, memberId, articleImage);

        return new BaseResponse(createArticleId);
    }

    @GetMapping(value = "/articles/{articleId}")
    public BaseResponse getArticle(@PathVariable Long articleId) {
        ArticleReadResponseDto articleReadResponseDto = articleApplicationService.getArticleById(articleId);
        return new BaseResponse(articleReadResponseDto);
    }
}
