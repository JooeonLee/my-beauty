package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.article.ArticleReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.mapper.CommentMapper;
import me.jooeon.mybeauty.domain.image.ImageService;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.fixture.ArticleFixture;
import me.jooeon.mybeauty.fixture.CommentFixture;
import me.jooeon.mybeauty.fixture.ExternalMemberDtoFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ArticleApplicationServiceTest {

    @InjectMocks
    private ArticleApplicationService articleApplicationService;

    @Mock
    private ArticleService articleService;
    @Mock
    private CommentService commentService;
    @Mock
    private CommentApplicationService commentApplicationService;
    @Mock
    private ImageService imageService;
    @Mock
    private MemberPort memberPort;

    @DisplayName("아티클ID를_통해_아티클_상세정보를_조회한다.")
    @Test
    void getArticleById() {

        // given
        Article testArticle = ArticleFixture.아티클_아이디포함(1L, "테스트_아티클_제목", "테스트_아티클_내용");

        ExternalMemberDto authorInfo = ExternalMemberDtoFixture.외부이용멤버(1L);

        List<Comment> comments = Arrays.asList(
                CommentFixture.댓글_아이디_생성시간_포함(1L, testArticle, "테스트_아티클_내용1", 2L),
                CommentFixture.댓글_아이디_생성시간_포함(2L, testArticle, "테스트_아티클_내용2", 3L),
                CommentFixture.댓글_아이디_생성시간_포함(3L, testArticle, "테스트_아티클_내용3", 4L),
                CommentFixture.댓글_아이디_생성시간_포함(4L, testArticle, "테스트_아티클_내용4", 5L)
        );

        long commentCount = comments.size();

        List<ExternalMemberDto> externalMembers = Arrays.asList(
                ExternalMemberDtoFixture.외부이용멤버(2L),
                ExternalMemberDtoFixture.외부이용멤버(3L),
                ExternalMemberDtoFixture.외부이용멤버(4L),
                ExternalMemberDtoFixture.외부이용멤버(5L)
        );

        Map<Long, MemberSimpleProfileDto> memberInfoMap = externalMembers.stream()
                .collect(Collectors.toMap(ExternalMemberDto::getMemberId, MemberSimpleProfileDto::fromExternal));

        List<CommentReadResponseDto> commentReadResponseDtos = comments.stream()
                .map(comment -> CommentMapper.toCommentReadResponseDto(comment, memberInfoMap.get(comment.getMemberId())))
                .toList();

        // Mocking
        when(articleService.getArticleById(testArticle.getId())).thenReturn(testArticle);
        when(memberPort.getExternalMemberById(any())).thenReturn(authorInfo);
        when(commentApplicationService.getCommentByArticleId(testArticle.getId())).thenReturn(commentReadResponseDtos);
        when(commentService.countCommentByArticleId(testArticle.getId())).thenReturn(commentCount);

        // when
        ArticleReadResponseDto result = articleApplicationService.getArticleById(testArticle.getId());

        // then
        assertThat(result.getComments().size()).isEqualTo(commentCount);
    }
}
