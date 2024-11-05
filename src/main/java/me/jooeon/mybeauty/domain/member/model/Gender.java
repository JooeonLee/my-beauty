package me.jooeon.mybeauty.domain.member.model;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }

        // todo 예외 처리 로직 추가
        throw new IllegalArgumentException("Unknown gender: " + value);
    }
}
