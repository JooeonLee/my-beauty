package me.jooeon.mybeauty.domain.review.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewSaveRequestDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewResponseDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewStatisticsDto;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewWithCosmeticResponseDto;
import me.jooeon.mybeauty.domain.review.model.mapper.ReviewMapper;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final CosmeticRepository cosmeticRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public long createReview(ReviewSaveRequestDto requestDto, long memberId, long cosmeticId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(EntityNotFoundException::new);

        Review review = requestDto.toEntity(member, cosmetic);

        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Transactional
    public long updateReview(ReviewSaveRequestDto requestDto, long memberId, long reviewId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);

        review.updateReview(requestDto.getStar(),
                requestDto.getContent(),
                requestDto.getOneLineReview());

        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();

    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewResponseDto> getReviewByCosmeticId(long cosmeticId, Pageable pageable) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);

        Slice<Review> reviewSlice = reviewRepository.findByCosmeticIdOrderByCreatedAtDesc(cosmetic.getId(), pageable);


        // todo likeCount 를 0이 아닌 실제 DB 에서 조회한 값으로 넣기 (Join 후 JPQL 직접 작성 필요)
        // 조회한 review 바탕으로 response dto 생성
        Slice<ReviewResponseDto> reviewResponseDtoSlice = reviewSlice
                .map(review -> ReviewMapper.toReviewResponseDto(review, 0));

        return new SliceResponse<>(reviewResponseDtoSlice.getContent(), reviewResponseDtoSlice.getPageable().getPageNumber(), reviewResponseDtoSlice.isLast());
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewResponseDto> getReviewByCosmeticId2(long cosmeticId, Pageable pageable) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);

        Slice<Object[]> resultSlice = reviewRepository.findByCosmeticIdOrderByCreatedAtDesc2(cosmetic.getId(), Status.ACTIVE, pageable);


        // 조회한 Object[] 바탕으로 response dto 생성
        Slice<ReviewResponseDto> reviewResponseDtoSlice = resultSlice
                .map(results -> {
                    Review review = (Review) results[0];
                    long likeCount = (long) results[1];
                    return ReviewMapper.toReviewResponseDto(review, likeCount);
                });

        return new SliceResponse<>(reviewResponseDtoSlice.getContent(), reviewResponseDtoSlice.getPageable().getPageNumber(), reviewResponseDtoSlice.isLast());

    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getUpTo3ReviewsByCosmeticId(long cosmeticId) {

        // todo 커스텀 예외 생성 후 예외 처리  필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Review> reviewSlice = reviewRepository.findByCosmeticIdOrderByCreatedAtDesc(cosmetic.getId(), pageable);
        List<Review> reviews = reviewSlice.getContent();

        // todo likeCount 를 0이 아닌 실제 DB 에서 조회한 값으로 넣기
        return reviews.stream()
                .map(review -> ReviewMapper.toReviewResponseDto(review, 0))
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewStatisticsDto getReviewStatisticsByCosmeticId(long cosmeticId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId).orElseThrow(IllegalArgumentException::new);

        return reviewRepository.findStatisticsByCosmeticId(cosmetic.getId());
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewWithCosmeticResponseDto> getReviewByMemberId(long memberId, Pageable pageable) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        Slice<Review> reviewSlice = reviewRepository.findByMemberIdOrderByCreatedAtDesc(member.getId(), pageable);

        // todo likeCount 를 0이 아닌 실제 DB 에서 조회한 값으로 넣기 (Join 후 JPQL 직접 작성 필요)
        // 조회한 review 바탕으로 response dto 생성
        Slice<ReviewWithCosmeticResponseDto> reviewWithCosmeticResponseDtoSlice = reviewSlice
                .map(review -> ReviewMapper.toReviewWithCosmeticResponseDto(review, 0));

        return new SliceResponse<>(reviewWithCosmeticResponseDtoSlice.getContent(), reviewWithCosmeticResponseDtoSlice.getNumber(), reviewWithCosmeticResponseDtoSlice.isLast());
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewWithCosmeticResponseDto> getReviewByMemberId2(long memberId, Pageable pageable) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        Slice<Object[]> resultSlice = reviewRepository.findByMemberIdOrderByCreatedAtDesc2(member.getId(), Status.ACTIVE, pageable);

        // 조회한 Object[] 바탕으로 response dto 생성
        Slice<ReviewWithCosmeticResponseDto> reviewWithCosmeticResponseDtoSlice = resultSlice
                .map(results -> {
                    Review review = (Review) results[0];
                    long likeCount = (long) results[1];
                    return ReviewMapper.toReviewWithCosmeticResponseDto(review, likeCount);
                });

        return new SliceResponse<>(reviewWithCosmeticResponseDtoSlice.getContent(), reviewWithCosmeticResponseDtoSlice.getNumber(), reviewWithCosmeticResponseDtoSlice.isLast());
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewWithCosmeticResponseDto> getReview(Pageable pageable) {

        Slice<Review> reviewSlice = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);

        // todo likeCount 를 0이 아닌 실제 DB 에서 조회한 값으로 넣기 (Join gn JPQL 직접 작성 필요)
        // 조회한 review 바탕으로 response dto 생성
        Slice<ReviewWithCosmeticResponseDto> reviewWithCosmeticResponseDtoSlice = reviewSlice
                .map(review -> ReviewMapper.toReviewWithCosmeticResponseDto(review, 0));

        return new SliceResponse<>(reviewWithCosmeticResponseDtoSlice.getContent(), reviewWithCosmeticResponseDtoSlice.getNumber(), reviewWithCosmeticResponseDtoSlice.isLast());
    }

    @Transactional(readOnly = true)
    public SliceResponse<ReviewWithCosmeticResponseDto> getReview2(Pageable pageable) {

        Slice<Object[]> resultSlice = reviewRepository.findAllByOrderByCreatedAtDesc2(Status.ACTIVE, pageable);

        // 조회한 review 바탕으로 response dto 생성
        Slice<ReviewWithCosmeticResponseDto> reviewWithCosmeticResponseDtoSlice = resultSlice
                .map(results -> {
                    Review review = (Review) results[0];
                    long likeCount = (long) results[1];
                    return ReviewMapper.toReviewWithCosmeticResponseDto(review, likeCount);
                });

        return new SliceResponse<>(reviewWithCosmeticResponseDtoSlice.getContent(), reviewWithCosmeticResponseDtoSlice.getNumber(), reviewWithCosmeticResponseDtoSlice.isLast());
    }

    @Transactional
    public long deleteReview(long memberId, long reviewId) {

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        // todo 커스텀 예외 생성 후 예외 처리 필요
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);

        review.deleteReview();

        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }
}
