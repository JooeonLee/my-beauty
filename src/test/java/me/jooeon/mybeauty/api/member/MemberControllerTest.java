//package me.jooeon.mybeauty.api.member;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import me.jooeon.mybeauty.domain.auth.application.CustomOAuth2UserService;
//import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
//import me.jooeon.mybeauty.domain.auth.presentation.handler.CustomAuthenticationSuccessHandler;
//import me.jooeon.mybeauty.domain.auth.utils.JwtUtil;
//import me.jooeon.mybeauty.domain.member.application.MemberService;
//import me.jooeon.mybeauty.domain.member.model.dto.MemberMyPageResponseDto;
//import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
//import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
//import me.jooeon.mybeauty.domain.member.presentation.MemberController;
//import me.jooeon.mybeauty.global.CommonControllerTest;
//import me.jooeon.mybeauty.global.common.config.SecurityConfig;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@Import(SecurityConfig.class)
//@AutoConfigureRestDocs
//@ActiveProfiles({"test", "secret"})
//@WebMvcTest(controllers = MemberController.class)
//public class MemberControllerTest extends CommonControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private MemberService memberService;
//
//    @MockBean
//    private MemberRepository memberRepository;
//
//
//    @Test
//    @DisplayName("마이페이지 정보를 성공적으로 조회한다.")
//    @WithMockUser
//    void getMemberMyPageInfo() throws Exception {
//
//        // given
//        Long expectedMemberId = 1L;
//        MemberSimpleProfileDto testProfile = new MemberSimpleProfileDto(
//                "테스트 닉네임",
//                27,
//                "MALE",
//                "복합성",
//                "테스트 이미지 URL"
//        );
//
//        MemberMyPageResponseDto expectedResponseDto = new MemberMyPageResponseDto(
//                testProfile,
//                "복합성",
//                "지성 두피",
//                "지성 모",
//                "여름 쿨톤",
//                50,
//                50
//        );
//
//
//        when(memberService.getMemberMyPageInfo(expectedMemberId)).thenReturn(expectedResponseDto);
//
//
//
//        // when
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/app/api/member")
//                        .header("Authorization", "Bearer your-bearer-token")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.isSuccess").value(true))
//                .andExpect(jsonPath("$.result").isNotEmpty())
//                .andDo(document("{class-name}/{method-name}",
//                        preprocessRequest(
//                                modifyHeaders() // 헤더 내용 수정
//                                        .remove("Content-Length")
//                                        .remove("Host"),
//
//                                prettyPrint()
//                        ),
//                        preprocessResponse(
//                                modifyHeaders()
//                                        .remove("Content-Length")
//                                        .remove("X-Content-Type-Options")
//                                        .remove("X-XSS-Protection")
//                                        .remove("Cache-Control")
//                                        .remove("Pragma")
//                                        .remove("Expires")
//                                        .remove("X-Frame-Options"),
//
//                                prettyPrint()
//                        ),
//                        responseFields(
//                                fieldWithPath("isSuccess")  // field path 추가
//                                        .type(Boolean.class)
//                                        .description("API 성공 여부"),
//                                fieldWithPath("statusCode").description("상태 코드"),
//                                fieldWithPath("result.profile.nickname").description("사용자 닉네임"),
//                                fieldWithPath("result.profile.age").description("사용자 나이"),
//                                fieldWithPath("result.profile.gender").description("사용자 성별"),
//                                fieldWithPath("result.profile.skinType").description("사용자 피부 타입"),
//                                fieldWithPath("result.profile.imageUrl").description("사용자 프로필 이미지 URL"),
//                                fieldWithPath("result.skinType").description("피부 타입"),
//                                fieldWithPath("result.hairType").description("머리 타입"),
//                                fieldWithPath("result.hairMoisture").description("모발 상태"),
//                                fieldWithPath("result.colorTone").description("컬러 톤"),
//                                fieldWithPath("result.point").description("사용자 포인트"),
//                                fieldWithPath("result.exp").description("경험치")
//                        ))
//                );
//
////        result.andExpect(status().isOk())
////                .andExpect(jsonPath("$.isSuccess").value(true))
////                .andExpect(jsonPath("$.result.profile.nickname").value("테스트 닉네임"))
////                .andDo(document("member-my-page-info", // 문서의 식별자
////                        preprocessRequest(prettyPrint()),  // 요청 데이터를 보기 좋게 출력
////                        preprocessResponse(prettyPrint()), // 응답 데이터를 보기 좋게 출력
////                        requestHeaders(                     // 요청 헤더 문서화
////                                headerWithName("Authorization").description("사용자 인증 및 인가를 위한 Bearer token")
////                        ),
////                        responseFields(                   // 응답 필드 문서화
////                                fieldWithPath("isSuccess").description("API 성공 여부"),
////                                fieldWithPath("responseCode").description("응답 코드"),
////                                fieldWithPath("responseMessage").description("응답 메시지"),
////                                fieldWithPath("result.profile.nickname").description("사용자 닉네임"),
////                                fieldWithPath("result.profile.age").description("사용자 나이"),
////                                fieldWithPath("result.profile.gender").description("사용자 성별"),
////                                fieldWithPath("result.profile.skinType").description("사용자 피부 타입"),
////                                fieldWithPath("result.profile.profileImageUrl").description("사용자 프로필 이미지 URL"),
////                                fieldWithPath("result.skinType").description("피부 타입"),
////                                fieldWithPath("result.scalpType").description("두피 타입"),
////                                fieldWithPath("result.hairType").description("모발 타입"),
////                                fieldWithPath("result.personalColor").description("퍼스널 컬러"),
////                                fieldWithPath("result.reviewCount").description("사용자 작성 리뷰 수"),
////                                fieldWithPath("result.scrapCount").description("사용자 스크랩 기사 수")
////                        )
////                ));
//    }
//
//
//}
