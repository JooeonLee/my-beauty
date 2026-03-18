package me.jooeon.mybeauty.domain.likes.application;

import me.jooeon.mybeauty.domain.likes.model.Likes;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class GenericLikesService {
    public <T, L extends Likes> boolean toggleLikes(
            Long memberId,
            Long targetId,
            Function<Long, Member> memberFetcher,
            Function<Long, T> targetFetcher,
            BiFunction<Long, Long, Optional<L>> likesFetcher,
            BiFunction<Member, T, L> likesCreator,
            Consumer<L> likesSaver
    ) {
        Member member = memberFetcher.apply(memberId);
        T target = targetFetcher.apply(targetId);
        Optional<L> likesOptional = likesFetcher.apply(memberId, targetId);

        if (likesOptional.isPresent()) {
            L likes = likesOptional.get();
            if (likes.getStatus() == Status.DELETED) {
                likes.restore();
                return true;
            } else {
                likes.softDelete();
                return false;
            }
        }

        L likes = likesCreator.apply(member, target);
        likesSaver.accept(likes);
        return true;
    }
}
