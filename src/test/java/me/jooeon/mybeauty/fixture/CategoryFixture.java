package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.global.common.model.enums.Status;

public class CategoryFixture {

    public static Category 카테고리() {

        return 카테고리("카테고리_이름");
    }

    public static Category 카테고리(String name) {
        return Category.builder()
                .name(name)
                .status(Status.ACTIVE)
                .build();
    }
}
