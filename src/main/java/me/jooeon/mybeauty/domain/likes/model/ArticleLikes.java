package me.jooeon.mybeauty.domain.likes.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.jooeon.mybeauty.domain.article.model.Article;

@Entity
@DiscriminatorValue("article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ArticleLikes extends Likes {

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
