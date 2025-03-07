package me.jooeon.mybeauty.domain.article.model.dto.comment;

import lombok.Builder;
import lombok.Getter;
import me.jooeon.mybeauty.domain.article.model.Comment;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {

    private final long commentId;
    private final long memberId;
    private final String content;
    private final LocalDate createdAt;

    @Builder
    public CommentResponseDto(long commentId, long memberId, String content, LocalDate createdAt) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .memberId(comment.getMemberId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toLocalDate())
                .build();
    }
}
