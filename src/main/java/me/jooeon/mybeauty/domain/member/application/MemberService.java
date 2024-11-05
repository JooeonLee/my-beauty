package me.jooeon.mybeauty.domain.member.application;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.MemberBeautyInfoUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberProfileUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(Long memberId, MemberProfileUpdateRequestDto requestDto) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        member.updateProfile(requestDto.getNickname(),
                requestDto.getGender(),
                requestDto.getBirthday(),
                requestDto.getSkinType(),
                requestDto.getProfileImageUrl());

        memberRepository.save(member);
    }

    @Transactional
    public void updateMemberBeautyInfo(Long memberId, MemberBeautyInfoUpdateRequestDto requestDto) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        member.updateMemberBeautyInfo(requestDto.getScalpType(),
                requestDto.getHairType(),
                requestDto.getPersonalColor(),
                requestDto.getDisplayInProfile());

        memberRepository.save(member);
    }
}
