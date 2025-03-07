package me.jooeon.mybeauty.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.member.model.Member;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Builder
public class ExternalMemberDto {

    private Long memberId;
    private String email;
    private String nickname;
    private String role;
    private String memberProfileImageUrl;
    private String gender;
    private LocalDate birthday;
    private String skinType;

    public ExternalMemberDto(Long memberId, String email, String nickname, String role, String memberProfileImageUrl, String gender, LocalDate birthday, String skinType) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.memberProfileImageUrl = memberProfileImageUrl;
        this.gender = gender;
        this.birthday = birthday;
        this.skinType = skinType;
    }

    // 정적 팩토리 메서드
    public static ExternalMemberDto from(Member member) {
        return ExternalMemberDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole().toString())
                .memberProfileImageUrl(member.getMemberProfileImageUrl())
                .gender(member.getGender().toString())
                .birthday(member.getBirthday())
                .skinType(member.getSkinType())
                .build();
    }
}
