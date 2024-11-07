package me.jooeon.mybeauty.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.member.application.MemberService;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.MemberBeautyInfoUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberMyPageResponseDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberProfileUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus;
import me.jooeon.mybeauty.global.common.util.CustomOAuth2UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/test")
    public BaseResponse<Long> getMember(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("=== Member Controller 진입 ===");
        log.info("Member Id = {}", customOAuth2User.getMemberId());
        Member member = memberRepository.findById(customOAuth2User.getMemberId())
                .orElseThrow(IllegalArgumentException::new);

        Long memberId = member.getId();

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, memberId);
    }

    @PatchMapping("")
    public BaseResponse updateMember(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                     @RequestBody MemberProfileUpdateRequestDto requestDto) {

        log.info("MemberController updateMember 진입");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        memberService.updateMember(memberId, requestDto);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @PatchMapping("/beauty-info")
    public BaseResponse updateMemberBeautyInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                                               @RequestBody MemberBeautyInfoUpdateRequestDto requestDto) {

        log.info("MemberController updateMemberBeautyInfo 진입");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        memberService.updateMemberBeautyInfo(memberId, requestDto);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("")
    public BaseResponse<MemberMyPageResponseDto> getMemberMyPageInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("MemberController getMemberProfile 진입");

        Long memberId = CustomOAuth2UserUtil.extractMemberId(customOAuth2User);
        MemberMyPageResponseDto responseDto = memberService.getMemberMyPageInfo(memberId);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, responseDto);
    }
}
