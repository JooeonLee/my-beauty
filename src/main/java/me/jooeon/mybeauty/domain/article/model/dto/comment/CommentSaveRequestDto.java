package me.jooeon.mybeauty.domain.article.model.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.global.common.model.enums.Status;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;

    // 테스트용 생성자
    @Builder
    public CommentSaveRequestDto(String content) {
        this.content = content;
    }

    public Comment toComment(long memberId, Article article) {
        return Comment.builder()
                .memberId(memberId)
                .article(article)
                .content(content)
                .status(Status.ACTIVE)
                .build();

    }
}
