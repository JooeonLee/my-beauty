package me.jooeon.mybeauty.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class MemberMyPageResponseDto {

    private MemberSimpleProfileDto profile;
    private String skinType;
    private String scalpType;
    private String hairType;
    private String personalColor;
    private int reviewCount;
    private int scrapCount;

    public MemberMyPageResponseDto(MemberSimpleProfileDto profile, String skinType, String scalpType, String hairType, String personalColor, int reviewCount, int scrapCount) {
        this.profile = profile;
        this.skinType = skinType;
        this.scalpType = scalpType;
        this.hairType = hairType;
        this.personalColor = personalColor;
        this.reviewCount = reviewCount;
        this.scrapCount = scrapCount;
    }
}
