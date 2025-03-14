package me.jooeon.mybeauty.domain.likes.application;

import me.jooeon.mybeauty.domain.article.application.CommentService;
import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.likes.model.repository.CommentLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.fixture.ArticleFixture;
import me.jooeon.mybeauty.fixture.CommentFixture;
import me.jooeon.mybeauty.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CommentLikesServiceTest {

    @InjectMocks
    private CommentLikesService commentLikesService;

    @Mock
    private MemberPort memberPort;
    @Mock
    private CommentService commentService;
    @Mock
    private CommentLikesRepository commentLikesRepository;

    @DisplayName("댓글좋아요_생성에_성공하면_생성에_성공했다는_결과를_반환한다.")
    @Test
    void toggleLikes() {

        // given
        Member testMember = MemberFixture.멤버();
        long testMemberId = 1L;
        Article testArticle = ArticleFixture.아티클_아이디포함(1L,
                "테스트_아티클",
                "테스트_아티클_내용");
        Comment testComment = CommentFixture.댓글_아이디포함(1L,
                testArticle,
                "테스트_댓글_내용",
                1L);

        boolean expectedResult = true;
        given(memberPort.getMemberById(any())).willReturn(testMember);
        given(commentService.findCommentById(testComment.getId())).willReturn(testComment);
        given(commentLikesRepository.findByMemberIdAndCommentId(testMemberId, testComment.getId())).willReturn(Optional.empty());

        // when
        boolean result = commentLikesService.toggleLikes(testMemberId, testComment.getId());

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
