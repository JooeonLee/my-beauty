package me.jooeon.mybeauty.docs;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class DocsUtils {
    public static ResponseFieldsSnippet commonResponseFields() {
        return responseFields(
                fieldWithPath("isSuccess").description("API 성공 여부"),
                fieldWithPath("responseCode").description("응답 코드"),
                fieldWithPath("responseMessage").description("응답 메시지")
        );
    }

    public static RequestHeadersSnippet commonRequestHeaders() {
        return requestHeaders(
                headerWithName("Authorization").description("사용자 인증 및 인가를 위한 Bearer Token")
        );
    }

    public static FieldDescriptor[] commonResponseFieldDescriptors() {
        return new FieldDescriptor[]{
                fieldWithPath("isSuccess").description("API 성공 여부"),
                fieldWithPath("responseCode").description("응답 코드"),
                fieldWithPath("responseMessage").description("응답 메시지")
        };
    }

    public static FieldDescriptor[] mergeFieldDescriptors(FieldDescriptor[] first, FieldDescriptor[] second) {
        FieldDescriptor[] merged = new FieldDescriptor[first.length + second.length];
        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return merged;
    }

    public static FieldDescriptor[] mergeResponseFields(FieldDescriptor... additionalFields) {
        return mergeFieldDescriptors(commonResponseFieldDescriptors(), additionalFields);
    }

}
