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
import me.jooeon.mybeauty.domain.review.model.dto.ReviewResponseDto;
import me.jooeon.mybeauty.domain.review.model.repository.ReviewRepository;
import me.jooeon.mybeauty.global.common.model.dto.SliceResponse;
import me.jooeon.mybeauty.global.common.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        // DB 초기화
        reviewRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        cosmeticRepository.deleteAllInBatch();

        // DB id 칼럼 초기화
        jdbcTemplate.execute("ALTER TABLE review ALTER COLUMN review_id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN member_id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE cosmetic ALTER COLUMN cosmetic_id RESTART WITH 1");
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
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .star(5)
                .content("테스트 리뷰 내용")
                .oneLineReview("테리트 리뷰 한줄평")
                .build();

        // when
        long savedReviewId = reviewService.createReview(requestDto, testMember.getId(), testCosmetic.getId());

        // then
        assertThat(savedReviewId).isEqualTo(1);

    }

    @Test
    void 등록_요청한_리뷰에_대한_화장품이_존재하지_않을_경우_예외가_발생한다() {

        // given
        Member testMember = 멤버();
        memberRepository.save(testMember);

        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .star(5)
                .content("테스트 리뷰 내용")
                .oneLineReview("테리트 리뷰 한줄평")
                .build();

        // when, then
        assertThatThrownBy(() -> reviewService.createReview(requestDto, testMember.getId(), 0L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 특정_화장품에_대한_리뷰_목록를_조회한다() {

        // given
        String email = "테스트_멤버@이메일.com";
        String name ="테스트_멤버_이름";
        List<Member> memberList = new ArrayList<>();
        for(int i=1; i<=5; i++) {
            Member testMember = 멤버(email, name + i);
            testMember.updateProfile("테스트_멤버_이름"+i, "MALE", DateUtil.convertStringToDate("19970609"), "복합성", "테스트_이미지_URL"+i);
            memberList.add(testMember);
            memberRepository.save(testMember);
        }

        Cosmetic testCosmetic = 화장품();
        cosmeticRepository.save(testCosmetic);

        List<Review> reviewList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            Review testReview = 리뷰(memberList.get(i), testCosmetic);
            reviewList.add(testReview);
            reviewRepository.save(testReview);
        }

        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        SliceResponse<ReviewResponseDto> responseDto = reviewService.getReviewByCosmeticId(testCosmetic.getId(), pageRequest);

        // then
        assertThat(responseDto.getContent().size()).isEqualTo(5);
    }

}
