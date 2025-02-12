package me.jooeon.mybeauty.domain.cosmetic.application;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.BrandRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.fixture.BrandFixture;
import me.jooeon.mybeauty.fixture.CategoryFixture;
import me.jooeon.mybeauty.fixture.CosmeticFixture;
import me.jooeon.mybeauty.global.s3.model.ContentType;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import me.jooeon.mybeauty.global.s3.utils.S3Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
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
    private ImageService imageService;

    @Mock
    private CosmeticRepository cosmeticRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private S3Util s3Util;

    @DisplayName("화장품_생성에_성공하면_생성된_화장품ID를_반환한다.")
    @Test
    void createCosmetic() {
        // given
        CosmeticSaveRequestDto requestDto = CosmeticSaveRequestDto.builder()
                .brandId(1L)
                .categoryId(1L)
                .name("테스트_화장품")
                .price(15000)
                .capacity(100F)
                .explanation("테스트_화장품_상세_설명")
                .build();

        MockMultipartFile testFile = new MockMultipartFile(
                "test",
                "test.jpeg",
                "image/jpeg",
                new byte[]{});

        String testSavedImageUrl = "테스트_화장품_이미지_URL";

        Brand testBrand = BrandFixture.브랜드();
        Category testCategory = CategoryFixture.카테고리();

        long expectedCosmeticId = 1L;
        given(brandRepository.findById(any())).willReturn(Optional.of(testBrand));
        given(categoryRepository.findById(any())).willReturn(Optional.of(testCategory));
        given(imageService.upload(any(MockMultipartFile.class), any())).willReturn(testSavedImageUrl);
        given(cosmeticRepository.save(any(Cosmetic.class)))
                .willAnswer(invocationOnMock -> {
                    Cosmetic cosmetic = invocationOnMock.getArgument(0);
                    ReflectionTestUtils.setField(cosmetic, "id", expectedCosmeticId);
                    return cosmetic;
                });

        // when
        long actualCosmeticId = cosmeticService.createCosmetic(requestDto, testFile);

        // then
        assertThat(actualCosmeticId).isEqualTo(expectedCosmeticId);
    }
}
