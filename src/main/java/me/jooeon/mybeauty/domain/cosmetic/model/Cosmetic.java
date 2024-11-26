package me.jooeon.mybeauty.domain.cosmetic.model;

import jakarta.persistence.*;
import lombok.*;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;
import me.jooeon.mybeauty.domain.review.model.Review;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cosmetic extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cosmetic_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private float capacity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String explanation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cosmeticImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "cosmetic")
    private Set<CosmeticTag> cosmeticTags = new HashSet<>();

    @OneToMany(mappedBy = "cosmetic")
    private Set<Review> reviews = new HashSet<>();

    @Builder
    public Cosmetic(String name, int price, float capacity, String explanation, String cosmeticImage, Brand brand, Category category) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.explanation = explanation;
        this.cosmeticImage = cosmeticImage;
        this.brand = brand;
        this.category = category;

        // default value
        this.status = Status.ACTIVE;
        this.cosmeticTags = new HashSet<>();
        this.reviews = new HashSet<>();
    }
}
