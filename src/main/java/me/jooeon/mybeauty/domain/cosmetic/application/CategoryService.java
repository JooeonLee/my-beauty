package me.jooeon.mybeauty.domain.cosmetic.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.category.CategoryResponseDto;
import me.jooeon.mybeauty.domain.cosmetic.model.dto.category.CategorySaveRequestDto;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories() {

        // 부모가 없는 카테고리 가져오기
        List<Category> rootCategories = categoryRepository.findAllByParentIdIsNull();

        return rootCategories.stream()
                .map(CategoryResponseDto::from)
                .toList();
    }

    public List<CategoryResponseDto> getAllCategoriesWithChildren() {

        // 부모가 없는 카테고리 가져오기
        List<Category> rootCategories = categoryRepository.findAllWithChildren();

        return rootCategories.stream()
                .map(CategoryResponseDto::from)
                .toList();
    }

    public CategoryResponseDto createCategory(CategorySaveRequestDto requestDto) {

        // todo 커스텀 예외 생성 후 헨들링 필요
        Category parentCategory = categoryRepository.findById(requestDto.getParentId())
                .orElseThrow(IllegalArgumentException::new);

        Category category = requestDto.toEntity(parentCategory);
        categoryRepository.save(category);
        return CategoryResponseDto.from(category);
    }
}
