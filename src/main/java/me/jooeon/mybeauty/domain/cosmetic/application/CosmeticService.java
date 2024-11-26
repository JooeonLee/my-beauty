package me.jooeon.mybeauty.domain.cosmetic.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSaveRequestDto;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.BrandRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.global.s3.model.dto.S3File;
import me.jooeon.mybeauty.global.s3.utils.S3Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CosmeticService {

    private final ImageService imageService;

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
        String imageUrl = imageService.upload(file);

        Cosmetic cosmetic = requestDto.toEntity(brand, category, imageUrl);

        Cosmetic savedCosmetic = cosmeticRepository.save(cosmetic);
        return savedCosmetic.getId();
    }
}
