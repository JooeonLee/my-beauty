package me.jooeon.mybeauty.domain.member.application;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;

import java.util.Collection;
import java.util.List;

public interface MemberPort {

    ExternalMemberDto getExternalMemberById(Long memberId);

    Member getMemberById(Long memberId);

    List<ExternalMemberDto> getExternalMembersByIds(Collection<Long> memberIds);
}
