package me.jooeon.mybeauty.api.comment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jooeon.mybeauty.docs.DocsUtils;
import me.jooeon.mybeauty.domain.article.application.CommentApplicationService;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentSaveRequestDto;
import me.jooeon.mybeauty.domain.article.presentation.CommentController;
import me.jooeon.mybeauty.global.CommonControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest extends CommonControllerTest {

    @MockBean
    private CommentApplicationService commentApplicationService;

    @Test
    @DisplayName("댓글을 성공적으로 생성한다.")
    @WithMockUser
    void saveComment() throws Exception {

        // given
        Long expectedCommentId = 1L;
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(
                "테스트 댓글 내용"
        );

        Long testArticleId = 1L;

        when(commentApplicationService.createComment(any(CommentSaveRequestDto.class), anyLong(), anyLong())).thenReturn(expectedCommentId);

        // when
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.post("/app/api/articles/{articleId}/comments", testArticleId)
                .header("Authorization", "Bearer your-bearer-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result").value(expectedCommentId))
                .andDo(document("comment-create",    // 문서 식별자
                        preprocessRequest(prettyPrint()),    // 요청 데이터 보기 좋게 출력
                        preprocessResponse(prettyPrint()),    // 응답 데이터 보기 좋게 출력
                        requestHeaders(    // 요청 헤더 문서화
                                headerWithName("Authorization").description("사용자 인증 및 인가를 위한 Bearer token")
                        ),
                        DocsUtils.commonRequestHeaders(),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Comment API")
                                        .summary("댓글 생성 API")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer 토큰 (예: `Bearer your-jwt-token`)")
                                        )
                                        .responseFields(
                                                DocsUtils.mergeResponseFields(
                                                        fieldWithPath("result").type("number").description("요청 결과 생성된 댓글 ID")
                                                )
                                        )
                                        .responseSchema(Schema.schema("CommentCreateResponseSchema"))
                                        .build()
                        )
                ));
    }
}
