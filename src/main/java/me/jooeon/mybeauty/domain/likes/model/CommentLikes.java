package me.jooeon.mybeauty.domain.likes.model;

import jakarta.persistence.*;
import me.jooeon.mybeauty.domain.article.model.Comment;

@Entity
@DiscriminatorValue("comment")
public class CommentLikes extends Likes {

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
