package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.application.CommentService;
import me.jooeon.mybeauty.domain.likes.model.CommentLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.CommentLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final MemberPort memberPort;
    private final CommentService commentService;
    private final GenericLikesService genericLikesService;

    @Transactional
    public boolean toggleLikes(Long memberId, Long commentId) {
        return genericLikesService.toggleLikes(
                memberId,
                commentId,
                memberPort::getMemberById,
                commentService::findCommentById,
                commentLikesRepository::findByMemberIdAndCommentId,
                (member, comment) -> CommentLikes.builder()
                        .member(member)
                        .comment(comment)
                        .status(Status.ACTIVE)
                        .build(),
                commentLikesRepository::save
        );
    }
}
