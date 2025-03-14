package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.application.ArticleService;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.likes.model.ArticleLikes;
import me.jooeon.mybeauty.domain.likes.model.CommentLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ArticleLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikesService {

    private final ArticleLikesRepository articleLikesRepository;
    private final MemberPort memberPort;
    private final ArticleService articleService;

    @Transactional
    public boolean toggleLikes(Long memberId, Long articleId) {

        Member member = memberPort.getMemberById(memberId);

        Article article = articleService.getArticleById(articleId);

        Optional<ArticleLikes> articleLikesOptional = articleLikesRepository.findByMemberIdAndArticleId(member.getId(), article.getId());

        if(articleLikesOptional.isPresent()) {
            ArticleLikes articleLikes = articleLikesOptional.get();

            if(articleLikes.getStatus() == Status.DELETED) {
                articleLikes.restore();
                return true;
            }
            else {
                articleLikes.softDelete();
                return false;
            }
        }

        ArticleLikes articleLikes = ArticleLikes.builder()
                .member(member)
                .article(article)
                .status(Status.ACTIVE)
                .build();
        articleLikesRepository.save(articleLikes);
        return true;
    }
}
