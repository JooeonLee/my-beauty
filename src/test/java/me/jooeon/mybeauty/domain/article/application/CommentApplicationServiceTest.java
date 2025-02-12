package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.dto.article.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.fixture.ArticleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class CommentApplicationServiceTest {

    @InjectMocks
    private CommentApplicationService commentApplicationService;

    @Mock
    private CommentService commentService;
    @Mock
    private ArticleService articleService;
    @Mock
    private MemberPort memberPort;

    @DisplayName("댓글_생성에_성공하면_생성된_아티클ID를_반환한다.")
    @Test
    void createComment() {
        // given
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .content("테스트_댓글")
                .build();

        long testExternalMemberId = 1L;
        ExternalMemberDto externalMemberDto = ExternalMemberDto.builder()
                .memberId(testExternalMemberId)
                .email("테스트@이메일")
                .nickname("테스트_닉네임")
                .role("ROLE_MEMBER")
                .gender("MALE")
                .build();

        long testArticleId = 1L;
        Article testArticle = ArticleFixture.아티클();

        given(memberPort.getExternalMemberById(testExternalMemberId)).willReturn(externalMemberDto);
        given(articleService.getArticleById(testArticleId)).willReturn(testArticle);

        long expectedCommentId = 1L;
        given(commentService.createComment(requestDto, testExternalMemberId, testArticle)).willReturn(expectedCommentId);

        // when
        long actualCommentId = commentApplicationService.createComment(requestDto, testArticleId, testExternalMemberId);

        // then
        assertThat(actualCommentId).isEqualTo(expectedCommentId);

    }
}
