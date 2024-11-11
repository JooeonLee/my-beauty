package me.jooeon.mybeauty.domain.review.application;

import jakarta.persistence.EntityNotFoundException;
import me.jooeon.mybeauty.domain.cosmetic.model.Brand;
import me.jooeon.mybeauty.domain.cosmetic.model.Category;
import me.jooeon.mybeauty.domain.cosmetic.model.Cosmetic;
import me.jooeon.mybeauty.domain.cosmetic.model.repository.CosmeticRepository;
import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.review.model.dto.ReviewCreateRequestDto;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static me.jooeon.mybeauty.fixture.CosmeticFixture.화장품;
import static me.jooeon.mybeauty.fixture.MemberFixture.멤버;
import static me.jooeon.mybeauty.fixture.ReviewFixture.리뷰;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CosmeticRepository cosmeticRepository;

    @Autowired
    private ReviewService reviewService;

    @AfterEach
    void tearDown() {

    }

    @Test
    void 요청한_내용으로_리뷰를_등록한다() {

        // given
        Member testMember = 멤버();
        memberRepository.save(testMember);

        Cosmetic testCosmetic = 화장품();
        Brand testBrand = testCosmetic.getBrand();
        Category testCategory = testCosmetic.getCategory();

        cosmeticRepository.save(testCosmetic);
        long testCosmeticId = testCosmetic.getId();
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .cosmeticId(testCosmeticId)
                .star(5)
                .content("테스트 리뷰 내용")
                .oneLineReview("테리트 리뷰 한줄평")
                .build();

        // when
        long savedReviewId = reviewService.createReview(requestDto, testMember.getId());

        // then
        assertThat(savedReviewId).isEqualTo(1);

    }

    @Test
    void 등록_요청한_리뷰에_대한_화장품이_존재하지_않을_경우_예외가_발생한다() {

        // given
        Member testMember = 멤버();
        memberRepository.save(testMember);

        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .cosmeticId(0L)
                .star(5)
                .content("테스트 리뷰 내용")
                .oneLineReview("테리트 리뷰 한줄평")
                .build();

        // when, then
        assertThatThrownBy(() -> reviewService.createReview(requestDto, testMember.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
