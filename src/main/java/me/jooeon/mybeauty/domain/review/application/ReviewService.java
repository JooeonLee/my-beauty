package me.jooeon.mybeauty.domain.review.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewCreateRequestDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewResponseDto;
import me.jooeon.mybeauty.domain.review.model.mapper.ReviewMapper;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final CosmeticRepository cosmeticRepository;
    private final ReviewRepository reviewRepository;

    public long createReview(ReviewCreateRequestDto requestDto, long memberId, long cosmeticId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(EntityNotFoundException::new);

        Review review = requestDto.toEntity(member, cosmetic);

        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewResponseDto> getReviewByCosmeticId(long cosmeticId, Pageable pageable) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);

        Slice<Review> reviewSlice = reviewRepository.findByCosmeticIdOrderByCreatedAtDesc(cosmetic.getId(), pageable);


        // todo likeCount 를 0이 아닌 실제 DB에서 조회한 값으로 넣기
        // 조회한 review 바탕으로 response dto 생성
        Slice<ReviewResponseDto> reviewResponseDtoSlice = reviewSlice
                .map(review -> ReviewMapper.toReviewResponseDto(review, 0));

        return new SliceResponse<>(reviewResponseDtoSlice.getContent(), reviewResponseDtoSlice.getPageable().getPageNumber(), reviewResponseDtoSlice.isLast());
    }
}
