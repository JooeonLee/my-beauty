package me.jooeon.mybeauty.docs;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class DocsUtils {
    public static ResponseFieldsSnippet commonResponseFields() {
        return responseFields(
                fieldWithPath("isSuccess").description("API 성공 여부"),
                fieldWithPath("responseCode").description("응답 코드"),
                fieldWithPath("responseMessage").description("응답 메시지"),
                fieldWithPath("result").description("요청 결과")
        );
    }

    public static RequestHeadersSnippet commonRequestHeaders() {
        return requestHeaders(
                headerWithName("Authorization").description("사용자 인증 및 인가를 위한 Bearer Token")
        );
    }
}
