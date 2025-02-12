package me.jooeon.mybeauty.domain.article.model;

import lombok.Getter;

@Getter
public enum ArticleType {

    ARTICLE("article"),
    QUESTION("question"),
    EVENT("event");

    ArticleType(String type) {
        this.type = type;
    }

    private final String type;
}
