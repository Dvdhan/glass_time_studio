package David.glass_time_studio.domain.review.service;

import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.booking.repository.BookingRepository;
import David.glass_time_studio.domain.booking.service.BookingService;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.domain.review.entity.Review;
import David.glass_time_studio.domain.review.repository.ReviewRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewService {
    private ReviewRepository reviewRepository;
    private BookingRepository bookingRepository;
    private MemberRepository memberRepository;
    private MemberService memberService;
    private BookingService bookingService;

    public ReviewService (ReviewRepository reviewRepository, BookingRepository bookingRepository,
                          MemberRepository memberRepository, MemberService memberService,
                          BookingService bookingService){
        this.reviewRepository=reviewRepository;
        this.bookingRepository=bookingRepository;
        this.memberRepository=memberRepository;
        this.memberService=memberService;
        this.bookingService=bookingService;
    }

    public Review postReview(Review review){
        Long memberId = review.getMemberId();
        Long bookingId = review.getBookingId();

        Booking checkingBooking = bookingRepository.findBookingStatusByMemberIdAndBookingId(memberId, bookingId);
        Review checkingReview = reviewRepository.findReviewByMemberIdAndBookingId(memberId, bookingId);


        // 조건 1. 해당 회원 아이디, 수업 예약 ID 로 상태 Y 인 수업 예약 기록이 있는지 조회
        if(checkingBooking == null)
        {
            throw new BusinessLogicException(ExceptionCode.LECTURE_HISTORY_WITH_MEMBER_ID_IS_NOT_FOUND);
        }
        else
        {
            // 조건 2. 해당 회원 아이디, 수업 아이디로 작성된 리뷰가 있는지 검토
            if(checkingReview != null)
            {
                throw new BusinessLogicException(ExceptionCode.REVIEW_ALREADY_EXIST);
            }
            else
            {
                Review postReview = setupReview(memberId, bookingId);
                postReview.setTitle(review.getTitle());
                postReview.setContent(review.getContent());
                return reviewRepository.save(postReview);
            }
        }
    }
    public Review setupReview(Long memberId, Long bookingId){
        Member member = memberService.findMemberById(memberId);
        Booking booking = bookingService.findBooking(bookingId);
        Review review = new Review();
        review.setBookingId(booking.getBookingId());
        review.setMemberId(member.getMemberId());
        return review;
    }
    public Page<Review> findAllMyReviews(int page, int size, Long memberId){
        Page<Review> reviewPage = reviewRepository.findAllMyReviews(PageRequest.of(page, size), memberId);
        return reviewPage;
    }

    public Page<Review> findAllReviews(int page, int size){
        Page<Review> reviewPage = reviewRepository.findAllReviews(PageRequest.of(page, size));
        return reviewPage;
    }
    public Review updateReview(Review review, Long memberId, Long reviewId){
        Review foundByMemberIdAndReviewId = reviewRepository.findReviewByMemberIdAndReviewId(memberId, reviewId);

        if(foundByMemberIdAndReviewId == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_ID_IS_NOT_MATCHED_WITH_REVIEW);
        }
        String oldTitle = foundByMemberIdAndReviewId.getTitle();
        String newTitle = review.getTitle();

        String oldContent = foundByMemberIdAndReviewId.getContent();
        String newContent = review.getContent();

        boolean isUpdated = false;

        if(newTitle!=null && oldTitle != newTitle){
            foundByMemberIdAndReviewId.setTitle(newTitle);
            isUpdated = true;
        }
        if(newContent!=null && oldContent != newContent){
            foundByMemberIdAndReviewId.setContent(newContent);
            isUpdated = true;
        }
        if(isUpdated){
            return reviewRepository.save(foundByMemberIdAndReviewId);
        }
        else {
            return foundByMemberIdAndReviewId;
        }
    }

    public Review findMyReview(Long memberId, Long reviewId){
        Review review = reviewRepository.findReviewByMemberIdAndReviewId(memberId, reviewId);
        if(review == null){
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }else {
            return review;
        }
    }
    public void deleteReq(Review review){
        reviewRepository.delete(review);
    }

    public Review findReviewById(Long reviewId){
        Review foundReview = reviewRepository.findReviewByReviewId(reviewId);
        if(foundReview == null){
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }
        else {
            return foundReview;
        }
    }
}
