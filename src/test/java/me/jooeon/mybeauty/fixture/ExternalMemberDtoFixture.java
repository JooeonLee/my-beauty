package me.jooeon.mybeauty.fixture;

import me.jooeon.mybeauty.domain.member.model.Gender;
import me.jooeon.mybeauty.domain.member.model.Role;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;

import java.time.LocalDate;

public class ExternalMemberDtoFixture {

    public static ExternalMemberDto 외부이용멤버(long memberId) {
        return 외부이용멤버(memberId, "테스트_외부멤버@이메일.com", "테스트_외부멤버_닉네임",
                Role.MEMBER.toString(), "테스트_외부멤버_이미지_URL", Gender.MALE.toString(),
                LocalDate.of(1997, 6, 9), "복합성");
    }

    public static ExternalMemberDto 외부이용멤버(long memberId, String email, String nickname,
                                           String role, String memberProfileImageUrl, String gender,
                                           LocalDate birthday, String skinType) {
        return ExternalMemberDto.builder()
                .memberId(memberId)
                .email(email)
                .nickname(nickname)
                .role(role)
                .memberProfileImageUrl(memberProfileImageUrl)
                .gender(gender)
                .birthday(birthday)
                .skinType(skinType)
                .build();
    }
}
