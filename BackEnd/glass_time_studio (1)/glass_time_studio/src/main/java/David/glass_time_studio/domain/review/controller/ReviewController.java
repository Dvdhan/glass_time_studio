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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 개별 리뷰 조회 (일반 고객)
    @GetMapping("/{reviewId}")
    public ResponseEntity findReviews(@PathVariable("reviewId")@Positive Long reviewId){
        Review review = reviewService.findReviewById(reviewId);
        ReviewDto.Response response = reviewMapper.reviewToReviewDtoResponse(review);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 나의 모든 리뷰 조회 (마이페이지용)
    @GetMapping("/allMyReviews/{memberId}")
    public ResponseEntity allMyReviews(@Positive @RequestParam int page,
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
    // 전체 리뷰 조회 (전체 게시글 조회용)
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

    // 내 리뷰 삭제하기 (일반 고객)
    @DeleteMapping("/delete/{memberId}/{reviewId}")
    public ResponseEntity deleteMyReview(@PathVariable("memberId")@Positive Long memberId,
                                         @PathVariable("reviewId")@Positive Long reviewId){
        Review review = reviewService.findMyReview(memberId, reviewId);
        reviewService.deleteReq(review);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "["+review.getTitle()+"] 게시글 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }

    // 리뷰 삭제하기 (관리자용)
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId")@Positive Long reviewId){
        Review review = reviewService.findReviewById(reviewId);
        reviewService.deleteReq(review);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "["+review.getTitle()+"] 게시글 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }

    // 리뷰 검색어 by 수업 이름
    @GetMapping("/searchLectureName")
    public ResponseEntity<List<ReviewDto.Response>> searchReviewsByLectureName(@RequestParam("keyword") String keyword){
        log.info("전달받은 검색어: "+keyword);
        List<Review> reviews = reviewService.searchReviewByLectureName(keyword);
        log.info("검색된 REVIEW {}",reviews);
        List<ReviewDto.Response> responses = reviews.stream()
                .map(reviewMapper::reviewToReviewDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
