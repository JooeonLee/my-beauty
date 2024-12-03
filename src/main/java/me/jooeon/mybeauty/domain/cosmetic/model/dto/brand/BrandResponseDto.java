package me.jooeon.mybeauty.domain.cosmetic.model.dto.brand;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Brand;

@Getter
@NoArgsConstructor
public class BrandResponseDto {

    private Long id;
    private String name;

    public BrandResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BrandResponseDto from(Brand brand) {
        return new BrandResponseDto(brand.getId(), brand.getName());
    }
}
