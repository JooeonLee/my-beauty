package me.jooeon.mybeauty.domain.cosmetic.application;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.BrandRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.fixture.BrandFixture;
import me.jooeon.mybeauty.fixture.CategoryFixture;
import me.jooeon.mybeauty.fixture.CosmeticFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CosmeticServiceTest {

    @InjectMocks
    private CosmeticService cosmeticService;

    @Mock
    private CosmeticRepository cosmeticRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @DisplayName("화장품_생성에_성공하면_생성된_화장품ID를_반환한다.")
    @Test
    void signUp() {
        // given
        CosmeticSaveRequestDto requestDto = CosmeticSaveRequestDto.builder()
                .brandId(1L)
                .categoryId(1L)
                .name("테스트_화장품")
                .price(15000)
                .capacity(100F)
                .explanation("테스트_화장품_상세_설명")
                .build();

        Brand testBrand = BrandFixture.브랜드();
        Category testCategory = CategoryFixture.카테고리();
        Cosmetic testCosmetic = CosmeticFixture.화장품(testBrand, testCategory);

        long expectedCosmeticId = 1L;
        given(brandRepository.findById(any())).willReturn(Optional.of(testBrand));
        given(categoryRepository.findById(any())).willReturn(Optional.of(testCategory));
        given(cosmeticRepository.save(any(Cosmetic.class)))
                .willAnswer(invocationOnMock -> {
                    Cosmetic cosmetic = invocationOnMock.getArgument(0);
                    ReflectionTestUtils.setField(cosmetic, "id", expectedCosmeticId);
                    return cosmetic;
                });

        // when
        long actualCosmeticId = cosmeticService.createCosmetic(requestDto);

        // then
        assertThat(actualCosmeticId).isEqualTo(expectedCosmeticId);
    }
}
