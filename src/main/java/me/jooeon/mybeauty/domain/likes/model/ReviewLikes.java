package me.jooeon.mybeauty.domain.likes.model;

import jakarta.persistence.*;
import me.jooeon.mybeauty.domain.reivew.model.Review;

@Entity
@DiscriminatorValue("review")
public class ReviewLikes extends Likes {

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
