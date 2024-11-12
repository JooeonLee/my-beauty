package me.jooeon.mybeauty.domain.cosmetic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;

@NoArgsConstructor
@Builder
@Getter
public class CosmeticSimpleInfoDto {

    private String brandName;
    private String cosmeticName;
    private String cosmeticImageUrl;

    public CosmeticSimpleInfoDto(String brandName, String cosmeticName, String cosmeticImageUrl) {
        this.brandName = brandName;
        this.cosmeticName = cosmeticName;
        this.cosmeticImageUrl = cosmeticImageUrl;
    }

    public static CosmeticSimpleInfoDto from(Cosmetic cosmetic) {
        return CosmeticSimpleInfoDto.builder()
                .brandName(cosmetic.getBrand().getName())
                .cosmeticName(cosmetic.getName())
                .cosmeticImageUrl(cosmetic.getCosmeticImage())
                .build();
    }
}
