package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.global.common.model.enums.Status;

public class CosmeticFixture {

    public static Cosmetic 화장품(Brand brand, Category category) {

        return 화장품(brand, category, "테스트 화장품 이름", 10000, 100);
    }

    public static Cosmetic 화장품(Brand brand, Category category, String name, int price, float capacity) {

        return Cosmetic.builder()
                .brand(brand)
                .category(category)
                .name(name)
                .price(price)
                .capacity(capacity)
                .explanation("테스트 화장품 설명")
                .cosmeticImage("테스트 화장품 이미지 URL")
                .status(Status.ACTIVE)
                .build();
    }
}
