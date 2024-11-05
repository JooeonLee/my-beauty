package me.jooeon.mybeauty.domain.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberProfileUpdateRequestDto {

    private String nickname;
    private String gender;
    private String birthday;

    // todo entity 단에서 enum으로 변경 고려해보기
    private String skinType;
    private String profileImageUrl;

}
