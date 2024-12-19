package me.jooeon.mybeauty.domain.article.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.ArticleRepository;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ImageService imageService;

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public long createArticle(ArticleSaveRequestDto requestDto, long memberId, MultipartFile articleImage) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        String articleImageUrl = null;
        if (!articleImage.isEmpty()) {
            articleImageUrl = imageService.upload(articleImage, ImagePrefix.ARTICLE);
        }

        Article article = requestDto.toEntity(member, articleImageUrl);

        Article savedArticle = articleRepository.save(article);
        return savedArticle.getId();
    }
}
