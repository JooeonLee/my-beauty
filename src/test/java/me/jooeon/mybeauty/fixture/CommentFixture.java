package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.global.common.model.enums.Status;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class CommentFixture {

    public static Comment 댓글(Article article) {
        return 댓글(article, "테스트_댓글_내용", 1L);
    }

    public static Comment 댓글(Article article, String content, long memberId) {
        return Comment.builder()
                .article(article)
                .memberId(memberId)
                .content(content)
                .status(Status.ACTIVE)
                .build();
    }

    public static Comment 댓글_아이디포함(long commentId, Article article, String content, long memberId) {
        Comment comment = 댓글(article, content, memberId);
        setId(comment, commentId);
        return comment;
    }

    public static Comment 댓글_아이디_생성시간_포함(long commentId, Article article, String content, long memberId) {
        Comment comment = 댓글(article, content, memberId);
        setId(comment, commentId);
        setCreatedAt(comment);
        return comment;
    }

    // Reflection 을 이용하여 ID 값을 강제 설정
    private static void setId(Comment comment, long id) {
        try {
            Field field = Comment.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(comment, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set id of comment", e);
        }
    }

    // Reflection 을 이용하여 생성 시간을 강제 설정
    private static void setCreatedAt(Comment comment) {
        try {
            Field field = Comment.class.getSuperclass().getDeclaredField("createdAt");
            field.setAccessible(true);
            field.set(comment, LocalDateTime.now());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set createdAt of comment", e);
        }
    }
}
