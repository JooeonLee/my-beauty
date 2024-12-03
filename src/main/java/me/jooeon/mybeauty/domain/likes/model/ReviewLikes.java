package me.jooeon.mybeauty.domain.likes.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.jooeon.mybeauty.domain.review.model.Review;

@Entity
@DiscriminatorValue("review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ReviewLikes extends Likes {

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
