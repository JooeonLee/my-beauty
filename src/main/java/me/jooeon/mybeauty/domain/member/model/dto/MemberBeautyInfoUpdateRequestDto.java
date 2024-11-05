package me.jooeon.mybeauty.domain.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberBeautyInfoUpdateRequestDto {

    // todo entity 단에서 enum 으로 변경 고려해보기
    private String scalpType;
    private String hairType;
    private String personalColor;
    private Boolean displayInProfile;
}
