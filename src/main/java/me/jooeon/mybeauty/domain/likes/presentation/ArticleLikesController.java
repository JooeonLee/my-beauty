package me.jooeon.mybeauty.domain.likes.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.likes.application.ArticleLikesService;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("app/api")
public class ArticleLikesController {

    private final ArticleLikesService articleLikesService;

    @PostMapping("/articles/{articleId}/likes/toggle")
    public BaseResponse<Boolean> toggleCommentLikes(
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        boolean liked = articleLikesService.toggleLikes(memberId, articleId);
        return new BaseResponse<>(liked);
    }
}
