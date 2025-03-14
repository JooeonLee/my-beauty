package me.jooeon.mybeauty.domain.likes.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.likes.application.CommentLikesService;
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
public class CommentLikesController {

    private final CommentLikesService commentLikesService;

    @PostMapping("/comments/{commentId}/likes/toggle")
    public BaseResponse<Boolean> toggleCommentLikes(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        boolean liked = commentLikesService.toggleLikes(memberId, commentId);
        return new BaseResponse<>(liked);
    }
}
