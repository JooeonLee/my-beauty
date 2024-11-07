package me.jooeon.mybeauty.domain.member.application;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.article.model.repository.ScrapRepository;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.MemberBeautyInfoUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberMyPageResponseDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberProfileUpdateRequestDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ScrapRepository scrapRepository;

    @Transactional
    public void updateMember(Long memberId, MemberProfileUpdateRequestDto requestDto) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        member.updateProfile(requestDto.getNickname(),
                requestDto.getGender(),
                DateUtil.convertStringToDate(requestDto.getBirthday()),
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

    public MemberMyPageResponseDto getMemberMyPageInfo(Long memberId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        MemberSimpleProfileDto memberSimpleProfileDto = MemberSimpleProfileDto.from(member);
        int reviewCount = (int) reviewRepository.countByMemberId(member.getId());
        int scrapCount = (int) scrapRepository.countByMemberId(member.getId());

        return MemberMyPageResponseDto.builder()
                .profile(memberSimpleProfileDto)
                .skinType(member.getSkinType())
                .scalpType(member.getScalpType())
                .hairType(member.getHairType())
                .personalColor(member.getPersonalColor())
                .reviewCount(reviewCount)
                .scrapCount(scrapCount)
                .build();
    }

}
