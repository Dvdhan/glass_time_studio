package David.glass_time_studio.domain.review.repository;


import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM REVIEW WHERE member_Id = :memberId AND booking_Id = :bookingId", nativeQuery = true)
    public Review findReviewByMemberIdAndBookingId(@Param("memberId") Long memberId, @Param("bookingId") Long bookingId);

    @Query(value = "SELECT * FROM REVIEW WHERE member_id = :memberId", nativeQuery = true)
    public Page<Review> findAllMyReviews(Pageable pageable, @Param("memberId")Long memberId);

    @Query(value = "SELECT * FROM REVIEW order by order_id desc", nativeQuery = true)
    public Page<Review> findAllReviews(Pageable pageable);

    @Query(value = "SELECT * FROM REVIEW WHERE member_id = :memberId AND review_id = :reviewId", nativeQuery = true)
    public Review findReviewByMemberIdAndReviewId(@Param("memberId") Long memberId, @Param("reviewId") Long reviewId);

    @Query(value = "SELECT * FROM REVIEW WHERE review_id = :reviewId", nativeQuery = true)
    public Review findReviewByReviewId(@Param("reviewId")Long reviewId);
}
