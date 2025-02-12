package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.ArticleRepository;
import me.jooeon.mybeauty.domain.cosmetic.application.CosmeticService;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ImageService imageService;

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private MemberRepository memberRepository;

    @DisplayName("아티클_생성에_성공하면_생성된_아티클ID를_반환한다.")
    @Test
    void createArticle() {
        // given
        ArticleSaveRequestDto requestDto = ArticleSaveRequestDto.builder()
                .title("테스트_아티클")
                .content("테스트_아티클_내용")
                .build();

        MockMultipartFile testFile = new MockMultipartFile(
                "test",
                "test.jpeg",
                "image/jpeg",
                new byte[]{});

        String testSavedImageUrl = "테스트_아티클_이미지_URL";

        Member testMember = MemberFixture.멤버();
        long testMemberId = 1L;

        long expectedArticleId = 1L;
        given(imageService.upload(any(MockMultipartFile.class), any())).willReturn(testSavedImageUrl);
        given(memberRepository.findById(any())).willReturn(Optional.of(testMember));
        given(articleRepository.save(any(Article.class)))
                .willAnswer(invocationOnMock -> {
                    Article article = invocationOnMock.getArgument(0);
                    ReflectionTestUtils.setField(article, "id", expectedArticleId);
                    return article;
                });

        // when
        long actualArticleId = articleService.createArticle(requestDto.getTitle(), requestDto.getContent(), testMemberId, testSavedImageUrl);

        // then
        assertThat(actualArticleId).isEqualTo(expectedArticleId);
    }
}
