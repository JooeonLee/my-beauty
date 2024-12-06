package me.jooeon.mybeauty.domain.cosmetic.model;

import jakarta.persistence.*;
import lombok.*;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Cosmetic> cosmetics = new ArrayList<>();

    // 부모 카테고리 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    // 자식 카테고리 설정
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Category> children = new HashSet<>();

    // 연관 관계 편의 메서드
    public void addChildCategory(Category child) {
        this.children.add(child);
        child.setParent(this);
    }

    private void setParent(Category parent) {
        this.parent = parent;
    }

    public void addCosmetic(Cosmetic cosmetic) {
        this.cosmetics.add(cosmetic);
        cosmetic.setCategory(this);
    }

    // 생성자 및 빌더
    @Builder
    public Category(String name, Status status, Category parent) {
        this.name = name;
        this.status = status;
        this.parent = parent;
        this.cosmetics = new ArrayList<>();
        this.children = new HashSet<>();
        if (parent != null) {
            parent.addChildCategory(this);
        }
    }
}
