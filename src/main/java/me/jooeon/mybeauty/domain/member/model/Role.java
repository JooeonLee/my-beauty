package me.jooeon.mybeauty.domain.member.model;

import lombok.Getter;

@Getter
public enum Role {

    GUEST("GUEST"),
    MEMBER("MEMBER"),
    EXPORT("EXPORT"),
    ADMIN("ADMIN");

    Role(String value) {
        this.value = value;
        this.role = PREFIX + value;
    }

    private final String PREFIX = "ROLE_";
    private final String value;
    private final String role;

}
