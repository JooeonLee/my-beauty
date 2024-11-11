package me.jooeon.mybeauty.domain.review.model;

import jakarta.persistence.*;
import lombok.*;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;
import me.jooeon.mybeauty.domain.member.model.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int star;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String oneLineReview;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cosmetic_id")
    private Cosmetic cosmetic;


    @OneToMany(mappedBy = "review")
    private List<ReviewLikes> reviewLikesList = new ArrayList<>();
}
