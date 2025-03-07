package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.repository.CommentRepository;
import me.jooeon.mybeauty.fixture.ArticleFixture;
import me.jooeon.mybeauty.fixture.CommentFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

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

    @DisplayName("게시글_ID_를 통해_댓글_조회에_성공하면_조회된_댓글_리스트를_반환한다.")
    @Test
    void findCommentByArticleId() {

        // given
        Article testArticle = ArticleFixture.아티클_아이디포함(1L, "테스트_아티클_제목", "테스트_아티클_내용");

        List<Comment> comments = Arrays.asList(
                CommentFixture.댓글_아이디포함(1L, testArticle, "테스트_아티클_내용1", 2L),
                CommentFixture.댓글_아이디포함(2L, testArticle, "테스트_아티클_내용2", 3L),
                CommentFixture.댓글_아이디포함(3L, testArticle, "테스트_아티클_내용3", 4L),
                CommentFixture.댓글_아이디포함(4L, testArticle, "테스트_아티클_내용4", 5L)
        );

        given(commentRepository.findByArticleId(testArticle.getId())).willReturn(comments);

        // when
        List<Comment> result = commentService.findCommentByArticleId(testArticle.getId());

        // then
        assertThat(result).hasSize(4);
        assertThat(result.get(0)).isEqualTo(comments.get(0));
        assertThat(result.get(1)).isEqualTo(comments.get(1));
        assertThat(result.get(2)).isEqualTo(comments.get(2));
        assertThat(result.get(3)).isEqualTo(comments.get(3));
    }

    @DisplayName("게시글_ID_를 통해_게시글에_등록된_댓글수를_조회한다.")
    @Test
    void countCommentByArticleId() {

        // given
        Article testArticle = ArticleFixture.아티클_아이디포함(1L, "테스트_아티클_제목", "테스트_아티클_내용");

        List<Comment> comments = Arrays.asList(
                CommentFixture.댓글_아이디포함(1L, testArticle, "테스트_아티클_내용1", 2L),
                CommentFixture.댓글_아이디포함(2L, testArticle, "테스트_아티클_내용2", 3L),
                CommentFixture.댓글_아이디포함(3L, testArticle, "테스트_아티클_내용3", 4L),
                CommentFixture.댓글_아이디포함(4L, testArticle, "테스트_아티클_내용4", 5L)
        );

        given(commentRepository.countByArticleId(testArticle.getId())).willReturn((long) comments.size());

        // when
        long result = commentService.countCommentByArticleId(testArticle.getId());

        // then
        assertThat(result).isEqualTo(comments.size());
    }
}
