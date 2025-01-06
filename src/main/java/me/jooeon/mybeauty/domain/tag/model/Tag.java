package me.jooeon.mybeauty.domain.tag.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.CosmeticTag;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tag extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Long id;

    @Column(nullable = false, name = "tag_value")
    private String value;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tag_type")
    private TagType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @OneToMany(mappedBy = "tag")
    private Set<CosmeticTag> cosmeticTags = new HashSet<>();
}
