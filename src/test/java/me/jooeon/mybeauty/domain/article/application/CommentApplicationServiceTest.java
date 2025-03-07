package me.jooeon.mybeauty.domain.article.application;

import me.jooeon.mybeauty.domain.article.model.Article;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.model.mapper.CommentMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


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

    @DisplayName("아티클ID를_통해_댓글_조회에_성공하면_댓글_응답형식으로_변환하여_반환한다")
    @Test
    void getCommentByArticleId() {

        // given
        Article testArticle = ArticleFixture.아티클_아이디포함(1L, "테스트_아티클_제목", "테스트_아티클_내용");

        List<Comment> comments = Arrays.asList(
                CommentFixture.댓글_아이디_생성시간_포함(1L, testArticle, "테스트_아티클_내용1", 2L),
                CommentFixture.댓글_아이디_생성시간_포함(2L, testArticle, "테스트_아티클_내용2", 3L),
                CommentFixture.댓글_아이디_생성시간_포함(3L, testArticle, "테스트_아티클_내용3", 4L),
                CommentFixture.댓글_아이디_생성시간_포함(4L, testArticle, "테스트_아티클_내용4", 5L)
        );

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

        when(articleService.getArticleById(testArticle.getId())).thenReturn(testArticle);
        when(commentService.findCommentByArticleId(testArticle.getId())).thenReturn(comments);
        when(memberPort.getExternalMembersByIds(any())).thenReturn(externalMembers);

        // when
        List<CommentReadResponseDto> result = commentApplicationService.getCommentByArticleId(testArticle.getId());

        // then
        assertThat(result.size()).isEqualTo(commentReadResponseDtos.size());
    }

}
