package me.jooeon.mybeauty.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("")
    public BaseResponse<Long> getMember(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("=== Member Controller 진입 ===");
        log.info("Member Id = {}", customOAuth2User.getMemberId());
        Member member = memberRepository.findById(customOAuth2User.getMemberId())
                .orElseThrow(IllegalArgumentException::new);

        Long memberId = member.getId();

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, memberId);
    }
}
