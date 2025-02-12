package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.ArticleType;
import me.jooeon.mybeauty.global.common.model.enums.Status;

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
}
