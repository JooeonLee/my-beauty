package me.jooeon.mybeauty.domain.cosmetic.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.cosmetic.application.CosmeticService;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
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
}
