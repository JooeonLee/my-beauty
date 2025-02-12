package me.jooeon.mybeauty.domain.article.application;

import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.s3.model.ImagePrefix;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleApplicationService {

    private final ArticleService articleService;
    private final MemberPort memberPort;
    private final ImageService imageService;

    public ArticleApplicationService(ArticleService articleService, MemberPort memberPort, ImageService imageService) {
        this.articleService = articleService;
        this.memberPort = memberPort;
        this.imageService = imageService;
    }

    public long createArticle(ArticleSaveRequestDto requestDto, long memberId, MultipartFile articleImage) {
        Member member = memberPort.getMemberById(memberId);

        String articleImageUrl = null;
        if (!articleImage.isEmpty()) {
            articleImageUrl = imageService.upload(articleImage, ImagePrefix.ARTICLE);
        }

        return articleService.createArticle(requestDto.getTitle(), requestDto.getContent(), member.getId(), articleImageUrl);

    }
}
