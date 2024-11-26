package me.jooeon.mybeauty.domain.cosmetic.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CosmeticSaveRequestDto {

    private long brandId;
    private long categoryId;
    private String name;
    private int price;
    private float capacity;
    private String explanation;

    // test용 생성자
    @Builder
    public CosmeticSaveRequestDto(long brandId, long categoryId, String name, int price, float capacity, String explanation) {
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.explanation = explanation;
    }

    public Cosmetic toEntity(Brand brand, Category category, String imageUrl) {

        return Cosmetic.builder()
                .brand(brand)
                .category(category)
                .cosmeticImage(imageUrl)
                .name(name)
                .price(price)
                .capacity(capacity)
                .explanation(explanation)
                .build();
    }
}
