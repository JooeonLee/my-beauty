package me.jooeon.mybeauty.domain.cosmetic.model.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.brand.BrandResponseDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CosmeticDetailResponseDto {
    private BrandResponseDto brandInfo;
    private ReviewStatisticsDto reviewStatistics;

    private String imageUrl;
    private String name;
    private String explanation;
    private int price;
    private float capacity;

    @Builder
    public CosmeticDetailResponseDto(Cosmetic cosmetic, BrandResponseDto brandInfo, ReviewStatisticsDto reviewStatistics) {
        this.brandInfo = brandInfo;
        this.reviewStatistics = reviewStatistics;

        this.imageUrl = cosmetic.getCosmeticImage();
        this.name = cosmetic.getName();
        this.explanation = cosmetic.getExplanation();
        this.price = cosmetic.getPrice();
        this.capacity = cosmetic.getCapacity();
    }
}
