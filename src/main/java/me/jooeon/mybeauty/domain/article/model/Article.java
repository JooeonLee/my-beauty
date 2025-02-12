package me.jooeon.mybeauty.domain.article.model;

import jakarta.persistence.*;
import lombok.*;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;
import me.jooeon.mybeauty.domain.likes.model.ArticleLikes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String articleImage;

    @Column(columnDefinition = "TEXT")
    private String articleVideo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType articleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @OneToMany(mappedBy = "article")
    @Builder.Default
    private List<ArticleLikes> articleLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @Builder.Default
    private List<Scrap> scraps = new ArrayList<>();

    private long memberId;
}
