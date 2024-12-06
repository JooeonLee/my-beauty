package me.jooeon.mybeauty.domain.likes.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.likes.application.ReviewLikesService;
import me.jooeon.mybeauty.domain.likes.model.dto.LikesToggleResponseDto;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class ReviewLikesController {

    private final ReviewLikesService reviewLikesService;

    @PostMapping("/reviews/{reviewId}/likes/toggle")
    public BaseResponse<Boolean> toggleReviewLikes(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("=== Review Likes Controller 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        boolean liked = reviewLikesService.toggleLikes(memberId, reviewId);
        return new BaseResponse<>(liked);
    }



}
