package me.jooeon.mybeauty.domain.likes.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesToggleResponseDto {

    private long targetId;
    private boolean liked;

    @Builder
    public LikesToggleResponseDto(long targetId, boolean liked) {
        this.targetId = targetId;
        this.liked = liked;
    }
}
