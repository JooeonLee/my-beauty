package me.jooeon.mybeauty.global.common.util;

import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;

public class CustomOAuth2UserUtil {

    public static Long extractMemberId(CustomOAuth2User customOAuth2User) {
        return customOAuth2User != null ? customOAuth2User.getMemberId() : null;
    }
}
