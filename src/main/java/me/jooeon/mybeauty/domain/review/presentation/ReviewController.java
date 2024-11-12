package me.jooeon.mybeauty.domain.review.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.review.application.ReviewService;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewCreateRequestDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewResponseDto;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/cosmetics/{cosmeticId}/reviews")
    public BaseResponse createReview(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                     @RequestBody ReviewCreateRequestDto requestDto,
                                     @PathVariable("cosmeticId") Long cosmeticId) {

        log.info("=== Review Controller createReview 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        Long createReviewId = reviewService.createReview(requestDto, memberId, cosmeticId);

        return new BaseResponse(BaseResponseStatus.SUCCESS, createReviewId);

    }

    @GetMapping("/cosmetics/{cosmeticId}/reviews")
    public BaseResponse<SliceResponse<ReviewResponseDto>> getReviewByCosmeticId(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                                                                @PathVariable("cosmeticId") Long cosmeticId,
                                                                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        log.info("=== Review Controller getReviewByCosmeticId 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        PageRequest pageRequest = PageRequest.of(page, size);

        SliceResponse<ReviewResponseDto> responseDto = reviewService.getReviewByCosmeticId(cosmeticId, pageRequest);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, responseDto);
    }
}
