package David.glass_time_studio.domain.booking.service;

import David.glass_time_studio.domain.announcement.entity.Announcement;
import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.booking.repository.BookingRepository;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class BookingService {
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository=bookingRepository;
    }

    // 예약 생성

    public Booking makeNewBooking(Booking booking){
        return bookingRepository.save(booking);
    }
    // 단일 예약 조회
    public Booking findBooking(Long bookingId){
        Optional<Booking> foundBooking = bookingRepository.findById(bookingId);
        Booking target = foundBooking.orElseThrow(()->new BusinessLogicException(ExceptionCode.BOOKING_NOT_FOUND));
        return target;
    }
    // 전체 예약 조회
    public Page<Booking> findAllBookings(int page, int size){
        return bookingRepository.findAllBookings(PageRequest.of(page, size));
    }
    // 예약자 검색
    public List<Booking> searchByName(String keyword){
        return bookingRepository.searchBooking("%" + keyword + "%");
    }
    // 예약 수정
    public Booking updateBooking(Booking booking, Long bookingId){
        Booking target = findBooking(bookingId);

        Long new_lectureId = booking.getLecture_Id();
        LocalDate new_request_date = booking.getRequestDate();
        String new_lectureRequestTime = booking.getRequestTime();
        Long new_lecturePeopleNumber = booking.getPeopleNumber();
        String new_booker_name = booking.getBookerName();
        String new_booker_mobile = booking.getMobile();
        String new_request_message = booking.getRequestMessage();

        boolean isUpdated = false;

        if(new_lectureId != null && (target.getLecture_Id() != new_lectureId)){
            target.setLecture_Id(new_lectureId);
            isUpdated = true;
        }
        if(new_request_date != null && (target.getRequestDate() != new_request_date)){
            target.setRequestDate(new_request_date);
            isUpdated = true;
        }
        if(new_lectureRequestTime != null && (target.getRequestTime() != new_lectureRequestTime)){
            target.setRequestTime(new_lectureRequestTime);
            isUpdated = true;
        }
        if(new_lecturePeopleNumber != null && (target.getPeopleNumber() != new_lecturePeopleNumber)){
            target.setPeopleNumber(new_lecturePeopleNumber);
            isUpdated = true;
        }
        if(new_booker_name != null && (target.getBookerName() != new_booker_name)){
            target.setBookerName(new_booker_name);
            isUpdated = true;
        }
        if(new_booker_mobile != null && (target.getMobile() != new_booker_mobile)){
            target.setMobile(new_booker_mobile);
            isUpdated = true;
        }
        if(new_request_message != null && (target.getRequestMessage() != new_request_message)){
            target.setRequestMessage(new_request_message);
            isUpdated = true;
        }
        if(isUpdated){
            return bookingRepository.save(target);
        }
        else{
            return target;
        }
    }
    // 예약 취소
    public void deleteBooking (Booking booking){
        bookingRepository.delete(booking);
    }
    public void deleteBookingById (Long bookingId){
        bookingRepository.deleteById(bookingId);
    }
}
