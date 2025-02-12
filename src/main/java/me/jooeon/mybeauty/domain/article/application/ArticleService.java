package me.jooeon.mybeauty.domain.article.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.ArticleType;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.ArticleRepository;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.common.exception.exception.article.ArticleException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public long createArticle(String title, String content, long memberId, String articleImageUrl) {

        Article article = Article.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .articleImage(articleImageUrl)
                .articleType(ArticleType.ARTICLE)
                .status(Status.ACTIVE)
                .build();

        // todo 검증 코드 추가
        //validate(article);

        Article savedArticle = articleRepository.save(article);
        return savedArticle.getId();
    }

    public Article getArticleById(long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(BaseResponseStatus.NONE_ARTICLE));
    }
}
