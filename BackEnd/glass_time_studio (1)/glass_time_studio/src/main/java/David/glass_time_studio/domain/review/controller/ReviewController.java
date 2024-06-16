package David.glass_time_studio.domain.review.controller;

import David.glass_time_studio.domain.order.dto.OrderDto;
import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import David.glass_time_studio.domain.review.dto.ReviewDto;
import David.glass_time_studio.domain.review.entity.Review;
import David.glass_time_studio.domain.review.mapper.ReviewMapper;
import David.glass_time_studio.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/review")
@Slf4j
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper){
        this.reviewService=reviewService;
        this.reviewMapper=reviewMapper;
    }

    // 리뷰 생성
    @PostMapping
    public ResponseEntity postReview(@RequestBody @Valid ReviewDto.Post post){
        Review review = reviewMapper.reviewDtoPostToReview(post);
        Review postedReview = reviewService.postReview(review);
        ReviewDto.Response response = reviewMapper.reviewToReviewDtoResponse(postedReview);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }

    // 나의 모든 리뷰 조회 (마이페이지)
    @GetMapping("/allMyReviews/{memberId}")
    public ResponseEntity allMyOrders(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size,
                                      @PathVariable("memberId")@Positive Long memberId){
        log.info("내 리뷰 조회 memberId: "+memberId);
        Page<Review> reviews = reviewService.findAllMyReviews(page-1, size, memberId);

        PageInfo pageInfo = new PageInfo(
                reviews.getNumber(),
                reviews.getSize(),
                reviews.getTotalElements(),
                reviews.getTotalPages());
        List<Review> reviewList = reviews.getContent();
        List<ReviewDto.Response> responses = reviewMapper.reviewsToReviewDtoResponse(reviewList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 전체 리뷰 조회 [관리자용]
    @GetMapping("/all")
    public ResponseEntity allReviews(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Review> reviews = reviewService.findAllReviews(page-1, size);
        PageInfo pageInfo = new PageInfo(
                reviews.getNumber(),
                reviews.getSize(),
                reviews.getTotalElements(),
                reviews.getTotalPages());
        List<Review> reviewList = reviews.getContent();
        List<ReviewDto.Response> responses = reviewMapper.reviewsToReviewDtoResponse(reviewList);
        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 리뷰 내용 수정
    @PatchMapping("/update/{memberId}/{reviewId}")
    public ResponseEntity updateMyReview(@PathVariable("memberId")@Positive Long memberId,
                                         @PathVariable("reviewId")@Positive Long reviewId,
                                         @RequestBody ReviewDto.Patch patch){
        Review review = reviewMapper.reviewDtoPatchToReview(patch);
        log.info("review 수정 {}", review);
        Review updatedReview = reviewService.updateReview(review, memberId, reviewId);
        ReviewDto.Response response = reviewMapper.reviewToReviewDtoResponse(updatedReview);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
