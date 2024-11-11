package me.jooeon.mybeauty.domain.review.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewCreateRequestDto;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final CosmeticRepository cosmeticRepository;
    private final ReviewRepository reviewRepository;

    public long createReview(ReviewCreateRequestDto requestDto, long memberId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        Cosmetic cosmetic =cosmeticRepository.findById(requestDto.getCosmeticId()).orElseThrow(EntityNotFoundException::new);

        Review review = requestDto.toEntity(member, cosmetic);

        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }
}
