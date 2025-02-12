package me.jooeon.mybeauty.global;

import me.jooeon.mybeauty.domain.auth.model.AuthDto;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.auth.utils.AuthenticationUtil;
import me.jooeon.mybeauty.domain.member.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

@Import(TestSecurityConfig.class)
@ActiveProfiles({"test", "secret"})
public abstract class CommonControllerTest extends RestDocsTestSupport{

    @MockBean
    CustomOAuth2User customOAuth2User;

    @MockBean
    AuthenticationUtil authenticationUtil;

    @BeforeEach
    void setUpAuthentication() {

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(AuthDto.builder()
                .memberId(1L)
                .providerId("테스트_프로바이더_Id")
                .role(Role.fromRole("ROLE_MEMBER"))
                .build());

        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
