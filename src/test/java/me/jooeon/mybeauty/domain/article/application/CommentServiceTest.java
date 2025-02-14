package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.article.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.CommentRepository;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.fixture.ArticleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("댓글_생성에_성공하면_생성된_댓글ID를_반환한다.")
    @Test
    void createComment() {

        // given
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .content("테스트_댓글")
                .build();

        Article testArticle = ArticleFixture.아티클();
        long testMemberId = 1L;

        long expectedCommentId = 1L;
        given(commentRepository.save(any(Comment.class)))
                .willAnswer(invocationOnMock -> {
                    Comment comment = invocationOnMock.getArgument(0);
                    ReflectionTestUtils.setField(comment, "id", expectedCommentId);
                    return comment;
                });

        // when
        long actualCommentId = commentService.createComment(requestDto, testMemberId, testArticle);

        // then
        assertThat(actualCommentId).isEqualTo(expectedCommentId);
    }
}
