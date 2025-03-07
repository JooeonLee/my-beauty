package me.jooeon.mybeauty.domain.article.model.dto.comment;

import lombok.Builder;
import lombok.Getter;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;

@Getter
public class CommentReadResponseDto {

    private final long commentId;
    private final String content;
    private final MemberSimpleProfileDto memberProfileInfo;
    private final String daysAgo;

    @Builder
    public CommentReadResponseDto(long commentId, String content, MemberSimpleProfileDto memberProfileInfo, String daysAgo) {
        this.commentId = commentId;
        this.content = content;
        this.memberProfileInfo = memberProfileInfo;
        this.daysAgo = daysAgo;
    }

    public static CommentReadResponseDto of(Comment comment, MemberSimpleProfileDto memberSimpleProfileDto, String daysAgo) {
        return CommentReadResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .memberProfileInfo(memberSimpleProfileDto)
                .daysAgo(daysAgo)
                .build();


    }
}
