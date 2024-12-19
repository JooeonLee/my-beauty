package me.jooeon.mybeauty.global.s3.model;

import lombok.Getter;

@Getter
public enum ImagePrefix {

    MEMBER("member/"),
    COSMETIC("cosmetic/"),
    ARTICLE("article/"),
    TEST("test/");

    ImagePrefix(String prefix) {
        this.value = prefix;
    }

    private final String value;
}
