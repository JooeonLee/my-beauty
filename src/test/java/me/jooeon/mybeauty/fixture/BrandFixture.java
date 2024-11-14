package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.global.common.model.enums.Status;

public class BrandFixture {

    public static Brand 브랜드() {
        return 브랜드("테스트_브랜드_이름");
    }

    public static Brand 브랜드(String name) {

        return Brand.builder()
                .name(name)
                .status(Status.ACTIVE)
                .build();
    }
}
