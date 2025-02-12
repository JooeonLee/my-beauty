package me.jooeon.mybeauty.domain.article.model.dto.article;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.ArticleType;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.common.model.enums.Status;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleSaveRequestDto {

    private String title;
    private String content;

    // 테스트용 생성자
    @Builder
    public ArticleSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity(long memberId, String articleImageUrl) {
        return Article.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .articleImage(articleImageUrl)
                .articleType(ArticleType.ARTICLE)
                .status(Status.ACTIVE)
                .build();
    }
}
