package me.jooeon.mybeauty.domain.likes.application;

import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.article.application.CommentService;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.cosmetic.application.CosmeticService;
import me.jooeon.mybeauty.domain.likes.model.CommentLikes;
import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.CommentLikesRepository;
import me.jooeon.mybeauty.domain.member.application.MemberPort;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.dto.ExternalMemberDto;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final MemberPort memberPort;
    private final CommentService commentService;

    @Transactional
    public boolean toggleLikes(Long memberId, Long commentId) {

        Member member = memberPort.getMemberById(memberId);

        Comment comment = commentService.findCommentById(commentId);

        Optional<CommentLikes> commentLikesOptional = commentLikesRepository.findByMemberIdAndCommentId(member.getId(), comment.getId());

        if(commentLikesOptional.isPresent()) {
            CommentLikes commentLikes = commentLikesOptional.get();

            if(commentLikes.getStatus() == Status.DELETED) {
                commentLikes.restore();
                return true;
            }
            else {
                commentLikes.softDelete();
                return false;
            }
        }

        CommentLikes commentLikes = CommentLikes.builder()
                .member(member)
                .comment(comment)
                .status(Status.ACTIVE)
                .build();
        commentLikesRepository.save(commentLikes);
        return true;
    }
}
