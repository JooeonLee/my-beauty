package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.application.ArticleService;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.likes.model.ArticleLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ArticleLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikesService {

    private final ArticleLikesRepository articleLikesRepository;
    private final MemberPort memberPort;
    private final ArticleService articleService;

    private final GenericLikesService genericLikesService;

    @Transactional
    public boolean toggleLikes(Long memberId, Long articleId) {
        return genericLikesService.toggleLikes(
                memberId,
                articleId,
                memberPort::getMemberById,
                articleService::getArticleById,
                articleLikesRepository::findByMemberIdAndArticleId,
                (member, article) -> ArticleLikes.builder()
                        .member(member)
                        .article(article)
                        .status(Status.ACTIVE)
                        .build(),
                articleLikesRepository::save
        );
    }
}
