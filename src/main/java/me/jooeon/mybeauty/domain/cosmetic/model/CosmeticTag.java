package me.jooeon.mybeauty.domain.cosmetic.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.common.Status;
import me.jooeon.mybeauty.common.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CosmeticTag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cosmetic_tag_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cosmetic_id")
    private Cosmetic cosmetic;
}
