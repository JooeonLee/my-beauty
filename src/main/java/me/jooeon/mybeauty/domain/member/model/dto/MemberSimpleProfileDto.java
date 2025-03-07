package me.jooeon.mybeauty.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.common.util.DateUtil;

@NoArgsConstructor
@Builder
@Getter
public class MemberSimpleProfileDto {

    private String nickname;
    private int age;
    private String gender;
    private String skinType;
    private String profileImageUrl;

    public MemberSimpleProfileDto(String nickname, int age, String gender, String skinType, String profileImageUrl) {
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.skinType = skinType;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberSimpleProfileDto from(Member member) {
        return MemberSimpleProfileDto.builder()
                .nickname(member.getNickname())
                .age(DateUtil.calculateAge(member.getBirthday()))
                .gender(member.getGender().getValue())
                .skinType(member.getSkinType())
                .profileImageUrl(member.getMemberProfileImageUrl())
                .build();
    }

    public static MemberSimpleProfileDto fromExternal(ExternalMemberDto externalMemberDto) {
        return MemberSimpleProfileDto.builder()
                .nickname(externalMemberDto.getNickname())
                .age(DateUtil.calculateAge(externalMemberDto.getBirthday()))
                .gender(externalMemberDto.getGender())
                .skinType(externalMemberDto.getSkinType())
                .profileImageUrl(externalMemberDto.getMemberProfileImageUrl())
                .build();
    }

}
