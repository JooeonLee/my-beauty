package me.jooeon.mybeauty.domain.cosmetic.infrastructure;

import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CategoryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepositoryImpl extends JpaRepository<Category, Long>, CategoryRepository {

//    @EntityGraph(attributePaths = "children")
//    List<Category> findByParentIdIsNull();

    @EntityGraph(attributePaths = {"children", "children.children"})
    @Query("select c from Category c where c.parent is null")
    List<Category> findAllWithChildren();
}
