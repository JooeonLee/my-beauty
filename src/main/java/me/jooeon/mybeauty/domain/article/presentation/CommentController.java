package me.jooeon.mybeauty.domain.article.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.application.CommentApplicationService;
import me.jooeon.mybeauty.domain.article.model.dto.article.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class CommentController {

    private final CommentApplicationService commentApplicationService;

    @PostMapping("/articles/{articleId}/comments")
    public BaseResponse createComment(@PathVariable Long articleId,
                                      @RequestBody CommentSaveRequestDto requestDto,
                                      @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("=== Comment Controller createComment 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        log.info("memberId={}", memberId);

        Long createCommentId = commentApplicationService.createComment(requestDto, articleId, memberId);

        return new BaseResponse(createCommentId);
    }
}
