package me.jooeon.mybeauty.domain.tag.model;

import lombok.Getter;

@Getter
public enum TagType {
    COSMETIC("COSMETIC"),
    ARTICLE("ARTICLE");

    private final String value;

    TagType(String value) {
        this.value = value;
    }

    public static TagType fromValue(String value) {
        for (TagType type : TagType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        // todo 예외 처리 로직 추가
        throw new IllegalArgumentException(value);
    }
}
