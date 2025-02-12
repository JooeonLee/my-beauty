package me.jooeon.mybeauty.domain.cosmetic.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticDetailResponseDto;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.brand.BrandResponseDto;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.BrandRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.review.application.ReviewService;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import me.jooeon.mybeauty.global.s3.utils.S3Util;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CosmeticService {

    private final ImageService imageService;
    private final ReviewService reviewService;

    private final CosmeticRepository cosmeticRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createCosmetic(CosmeticSaveRequestDto requestDto, MultipartFile file) {

        long brandId = requestDto.getBrandId();
        long categoryId = requestDto.getCategoryId();

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Brand brand = brandRepository.findById(brandId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Category category = categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        String imageUrl = imageService.upload(file, ImagePrefix.COSMETIC);

        Cosmetic cosmetic = requestDto.toEntity(brand, category, imageUrl);

        Cosmetic savedCosmetic = cosmeticRepository.save(cosmetic);
        return savedCosmetic.getId();
    }

    @Transactional(readOnly = true)
    public CosmeticDetailResponseDto getCosmeticDetailInfoById(long cosmeticId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);

        ReviewStatisticsDto reviewStatistics = reviewService.getReviewStatisticsByCosmeticId(cosmetic.getId());

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Brand brand = brandRepository.findById(cosmetic.getId()).orElseThrow(IllegalArgumentException::new);

        BrandResponseDto brandResponseDto = BrandResponseDto.from(brand);

        return CosmeticDetailResponseDto.builder()
                .brandInfo(brandResponseDto)
                .reviewStatistics(reviewStatistics)
                .cosmetic(cosmetic)
                .build();

    }

    @Transactional(readOnly = true)
    public SliceResponse<CosmeticDetailResponseDto> getCosmeticDetailInfoByCategoryId(long categoryId, Pageable pageable) {

        Slice<Object[]> results = cosmeticRepository.findWithReviewStatsAndBrandByCategoryId(categoryId, pageable);

        List<CosmeticDetailResponseDto> responseDtos = results.stream()
                .map(result -> CosmeticDetailResponseDto.builder()
                        .cosmetic((Cosmetic) result[0])
                        .brandInfo(BrandResponseDto.from((Brand) result[1]))
                        .reviewStatistics(new ReviewStatisticsDto((double) result[2], (long) result[3]))
                        .build())
                .toList();

        return new SliceResponse<>(responseDtos, results.getNumber(), results.isLast());
    }
}
