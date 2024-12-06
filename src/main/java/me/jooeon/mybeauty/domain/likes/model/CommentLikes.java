package me.jooeon.mybeauty.domain.likes.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.jooeon.mybeauty.domain.article.model.Comment;

@Entity
@DiscriminatorValue("comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class CommentLikes extends Likes {

    // 연관 관계 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
