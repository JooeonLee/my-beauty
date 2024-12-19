package me.jooeon.mybeauty.domain.article.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.application.ArticleService;
import me.jooeon.mybeauty.domain.article.model.Article;
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

    @PostMapping(value = "/articles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse createArticle(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                      @RequestPart("requestDto") ArticleSaveRequestDto requestDto,
                                      @RequestPart(value = "articleImage", required = false) MultipartFile articleImage) {

        log.info("=== Article Controller createArticle 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        Long createArticleId = articleService.createArticle(requestDto, memberId, articleImage);

        return new BaseResponse(createArticleId);
    }
}
