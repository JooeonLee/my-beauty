package me.jooeon.mybeauty.domain.member.infrastructure;

import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.common.exception.exception.member.MemberException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberAdapter implements MemberPort {

    private final MemberRepository memberRepository;

    public MemberAdapter(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public ExternalMemberDto getExternalMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(BaseResponseStatus.NONE_MEMBER));

        return ExternalMemberDto.from(member);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(BaseResponseStatus.NONE_MEMBER));
    }
}
