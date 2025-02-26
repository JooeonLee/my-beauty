package me.jooeon.mybeauty.global.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSchemas(
                                        "BaseResponseSchema",
                                        new Schema<>()
                                                .addProperty("isSuccess", new BooleanSchema().description("API 성공 여부")
                                                .addProperty("responseCode", new IntegerSchema().description("응답 코드")))
                                                .addProperty("responseMessage", new StringSchema().description("응답 메시지"))
                                                .addProperty("result", new Schema<>().description("응답 결과(API 별로 다른 값)")).nullable(true))
                );
    }
}
