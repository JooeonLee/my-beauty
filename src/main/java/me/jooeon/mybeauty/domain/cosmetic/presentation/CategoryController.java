package me.jooeon.mybeauty.domain.cosmetic.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.cosmetic.application.CategoryService;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.category.CategoryResponseDto;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.category.CategorySaveRequestDto;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public BaseResponse<List<CategoryResponseDto>> getAllCategories(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
            ) {

        log.info("=== CategoryController getAllCategories 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        List<CategoryResponseDto> responseDtos = categoryService.getAllCategories();

        return new BaseResponse<>(responseDtos);
    }

    @GetMapping(value = "/categories2")
    public BaseResponse<List<CategoryResponseDto>> getAllCategoriesWithChildren(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {

        log.info("=== CategoryController getAllCategoriesWithChildren 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        List<CategoryResponseDto> responseDtos = categoryService.getAllCategoriesWithChildren();

        return new BaseResponse<>(responseDtos);
    }

    @PostMapping(value = "/categories")
    public BaseResponse<CategoryResponseDto> createCategory(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @RequestBody CategorySaveRequestDto requestDto
    ) {
        log.info("=== CategoryController createCategory 진입 ===");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);

        CategoryResponseDto responseDto = categoryService.createCategory(requestDto);

        return new BaseResponse<>(responseDto);
    }
}
