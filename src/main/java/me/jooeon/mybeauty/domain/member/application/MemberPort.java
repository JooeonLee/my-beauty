package me.jooeon.mybeauty.domain.member.application;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;

public interface MemberPort {

    ExternalMemberDto getExternalMemberById(Long memberId);

    Member getMemberById(Long memberId);
}
