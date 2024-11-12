package me.jooeon.mybeauty.domain.review.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.CosmeticSimpleInfoDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewWithCosmeticResponseDto {
    private CosmeticSimpleInfoDto cosmeticInfo;
    private ReviewResponseDto reviewInfo;

    @Builder
    public ReviewWithCosmeticResponseDto(CosmeticSimpleInfoDto cosmeticInfo, ReviewResponseDto reviewInfo) {
        this.cosmeticInfo = cosmeticInfo;
        this.reviewInfo = reviewInfo;
    }
}
