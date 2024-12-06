package me.jooeon.mybeauty.domain.cosmetic.model.dto.category;

import lombok.Builder;
import lombok.Getter;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.global.common.model.enums.Status;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
@Builder
public class CategorySaveRequestDto {

    private String name;
    private long parentId;

    public Category toEntity(Category parentCategory) {
        return Category.builder()
                .name(name)
                .parent(parentCategory)
                .status(Status.ACTIVE)
                .cosmetics(new ArrayList<>())
                .children(new HashSet<>())
                .build();
    }
}
