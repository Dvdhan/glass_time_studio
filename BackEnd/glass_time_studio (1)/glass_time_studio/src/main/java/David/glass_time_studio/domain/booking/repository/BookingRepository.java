package David.glass_time_studio.domain.booking.repository;

import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT * FROM BOOKING ORDER BY booking_id", nativeQuery = true)
    public Page<Booking> findAllBookings(Pageable pageable);

    @Query(value = "SELECT * FROM BOOKING WHERE status = 'Y' and booker_name LIKE :keyword", nativeQuery = true)
    public List<Booking> searchBooking_Y(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM BOOKING WHERE status = 'N' and booker_name LIKE :keyword", nativeQuery = true)
    public List<Booking> searchBooking_N(@Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query(value = "UPDATE BOOKING SET status = 'Y' WHERE booking_Id = :bookingId", nativeQuery = true)
    public void confirmRSVN(@Param("bookingId") Long bookingId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE BOOKING SET status = 'N' WHERE booking_Id = :bookingId", nativeQuery = true)
    public void cancelRSVN(@Param("bookingId") Long bookingId);

    @Query(value = "SELECT * FROM BOOKING WHERE member_Id = :memberId", nativeQuery = true)
    public Booking findMyBooking(@Param("memberId") Long memberId);

    @Query(value = "SELECT * FROM BOOKING WHERE member_Id = :memberId AND booking_Id = :bookingId", nativeQuery = true)
    public Booking findMyBookingByMemberIdAndBookingId(@Param("memberId") Long memberId, @Param("bookingId") Long bookingId);

    @Query(value = "SELECT * FROM BOOKING WHERE member_Id = :memberId", nativeQuery = true)
    public Page<Booking> findAllBookingsWithMemberId(Pageable pageable, @Param("memberId") Long memberId);

    @Query(value = "SELECT * FROM BOOKING WHERE member_Id = :memberId AND status='Y' AND booking_Id = :bookingId", nativeQuery = true)
    public Booking findBookingStatusByMemberIdAndBookingId(@Param("memberId") Long memberId, @Param("bookingId") Long bookingId);

}
