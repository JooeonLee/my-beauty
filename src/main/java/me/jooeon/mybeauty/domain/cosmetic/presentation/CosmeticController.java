package me.jooeon.mybeauty.domain.cosmetic.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.cosmetic.application.CosmeticService;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticDetailResponseDto;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class CosmeticController {

    private final CosmeticService cosmeticService;

    @PostMapping(value = "/cosmetics", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<Long> createCosmetic(
            @RequestPart("requestDto") CosmeticSaveRequestDto requestDto,
            @RequestPart("cosmeticImage") MultipartFile cosmeticImage,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {


        log.info("=== Cosmetic Controller createCosmetic 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        Long createCosmeticId = cosmeticService.createCosmetic(requestDto, cosmeticImage);

        return new BaseResponse<>(createCosmeticId);

    }

    @GetMapping(value = "/cosmetics/{cosmeticId}")
    public BaseResponse<CosmeticDetailResponseDto> getCosmeticDetailInfoById(@PathVariable("cosmeticId") Long cosmeticId,
                                                                       @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("=== Cosmetic Controller getCosmeticInfoById 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        CosmeticDetailResponseDto responseDto = cosmeticService.getCosmeticDetailInfoById(cosmeticId);

        return new BaseResponse<>(responseDto);
    }

    @GetMapping(value = "/cosmetics/categories/{categoryId}")
    public BaseResponse<SliceResponse<CosmeticDetailResponseDto>> getCosmeticDetailInfoByCategoryId(@PathVariable("categoryId") Long categoryId,
                                                                                                    @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                                                                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        log.info("=== Cosmetic Controller getCosmeticDetailIntoByCategoryId 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        PageRequest pageRequest = PageRequest.of(page, size);

        SliceResponse<CosmeticDetailResponseDto> responseDto = cosmeticService.getCosmeticDetailInfoByCategoryId(categoryId, pageRequest);

        return new BaseResponse<>(responseDto);
    }

}
