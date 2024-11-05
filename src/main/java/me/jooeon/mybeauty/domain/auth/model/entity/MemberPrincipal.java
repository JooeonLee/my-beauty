package me.jooeon.mybeauty.domain.auth.model.entity;

import me.jooeon.mybeauty.domain.member.model.Role;

public interface MemberPrincipal {
    String getEmail();
    Role getRole();
    String getProviderId();
    Long getMemberId();
    String getName();
}
