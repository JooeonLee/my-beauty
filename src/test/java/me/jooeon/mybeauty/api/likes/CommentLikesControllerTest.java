package me.jooeon.mybeauty.api.likes;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import me.jooeon.mybeauty.docs.DocsUtils;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.likes.application.CommentLikesService;
import me.jooeon.mybeauty.domain.likes.presentation.CommentLikesController;
import me.jooeon.mybeauty.global.CommonControllerTest;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentLikesController.class)
public class CommentLikesControllerTest extends CommonControllerTest {

    @MockBean
    private CommentLikesService commentLikesService;

    @Test
    @DisplayName("댓글에_대한_좋아요를_성공적으로_토글한다.")
    @WithMockUser
    public void toggleCommentLikes() throws Exception {

        // given
        Boolean expectedResult = true;

        Long testCommentId = 1L;
        Long testMemberId = 10L;


        try(MockedStatic<CustomOAuth2UserUtil> mockedStatic = Mockito.mockStatic(CustomOAuth2UserUtil.class)) {
            mockedStatic.when(() -> CustomOAuth2UserUtil.extractMemberId(any())).thenReturn(testMemberId);
            given(commentLikesService.toggleLikes(testMemberId, testCommentId)).willReturn(expectedResult);

            // when
            ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.post("/app/api/comments/{commentId}/likes/toggle", testCommentId)
                    .header("Authorization", "Bearer your-bearer-token")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.isSuccess").value(true))
                    .andExpect(jsonPath("$.result").value(expectedResult))
                    .andDo(document("comment-likes-toggle",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestHeaders(
                                    headerWithName("Authorization").description("사용자 인증 및 인가를 위한 Bearer token")
                            ),
                            DocsUtils.commonRequestHeaders(),
                            resource(
                                    ResourceSnippetParameters.builder()
                                            .tag("Comment Likes API")
                                            .summary("댓글 좋아요 토글 API")
                                            .requestHeaders(
                                                    headerWithName("Authorization").description("Bearer 토큰 (예: `Bearer your-jwt-token`)")
                                            )
                                            .responseSchema(Schema.schema("BaseResponseSchema"))
                                            .responseFields(
                                                    fieldWithPath("isSuccess").description("API 성공 여부"),
                                                    fieldWithPath("responseCode").description("응답 코드"),
                                                    fieldWithPath("responseMessage").description("응답 메서드"),
                                                    fieldWithPath("result").description("요청 결과 댓글 토글 성공 여부")
                                            )
                                            .build()
                            ),
                            DocsUtils.commonResponseFields()
                    ));
        }
    }
}
