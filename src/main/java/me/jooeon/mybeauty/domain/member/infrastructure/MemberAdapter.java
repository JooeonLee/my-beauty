package me.jooeon.mybeauty.domain.member.infrastructure;

import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.global.common.exception.exception.member.MemberException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberAdapter implements MemberPort {

    private final MemberRepository memberRepository;

    public MemberAdapter(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public ExternalMemberDto getExternalMemberById(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberException(BaseResponseStatus.NONE_MEMBER));

        Member member = getMemberById(memberId);
        return ExternalMemberDto.from(member);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(BaseResponseStatus.NONE_MEMBER));
    }

    @Override
    public List<ExternalMemberDto> getExternalMembersByIds(Collection<Long> memberIds) {
        return memberRepository.findByIdIn(memberIds).stream()
                .map(ExternalMemberDto::from)
                .collect(Collectors.toList());
    }
}
