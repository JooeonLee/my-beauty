package me.jooeon.mybeauty.domain.cosmetic.model.dto.category;

import lombok.Builder;
import lombok.Getter;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;

import java.util.List;

@Getter
@Builder
public class CategoryResponseDto {
    private long id;
    private String name;
    private List<CategoryResponseDto> children;

    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream()
                        .map(CategoryResponseDto::from)
                        .toList())
                .build();
    }
}
