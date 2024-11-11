package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.Role;

public class MemberFixture {

    public static Member 멤버() {
        return 멤버("테스트_멤버@이메일.com", "테스트_멤버_이름");
    }

    public static Member 멤버(String email, String name) {
        return Member.of(email, name, Role.MEMBER);
    }
}
