package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.ArticleType;
import me.jooeon.mybeauty.global.common.model.enums.Status;

import java.lang.reflect.Field;

public class ArticleFixture {

    public static Article 아티클() {
        return 아티클("테스트_아티클_제목", "테스트_아티클_내용");
    }

    public static Article 아티클(String title, String content) {
        return Article.builder()
                .title(title)
                .content(content)
                .articleType(ArticleType.ARTICLE)
                .memberId(1L)
                .status(Status.ACTIVE)
                .build();
    }

    public static Article 아티클_아이디포함(long articleId, String title, String content) {
        Article article = 아티클(title, content);
        setId(article, articleId);
        return article;
    }

    // Reflection 을 이용하여 ID 값을 강제 설정
    private static void setId(Article article, long id) {
        try {
            Field field = Article.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(article, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set id of article", e);
        }
    }
}
