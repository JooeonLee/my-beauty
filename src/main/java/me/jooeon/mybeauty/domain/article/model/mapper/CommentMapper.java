package me.jooeon.mybeauty.domain.article.model.mapper;

import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentReadResponseDto;
import me.jooeon.mybeauty.domain.article.model.dto.comment.CommentResponseDto;
import me.jooeon.mybeauty.domain.member.model.dto.MemberSimpleProfileDto;
import me.jooeon.mybeauty.global.common.util.DateUtil;

public class CommentMapper {

    public static CommentReadResponseDto toCommentReadResponseDto(Comment comment, MemberSimpleProfileDto memberSimpleProfileDto) {

        String daysAgo = DateUtil.formatElapsedTime(comment.getCreatedAt().toLocalDate());

        return CommentReadResponseDto.of(comment, memberSimpleProfileDto, daysAgo);
    }
}
