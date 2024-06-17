package David.glass_time_studio.domain.booking.controller;


import David.glass_time_studio.domain.announcement.dto.AnnouncementDto;
import David.glass_time_studio.domain.booking.dto.BookingDto;
import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.booking.mapper.BookingMapper;
import David.glass_time_studio.domain.booking.service.BookingService;
import David.glass_time_studio.domain.lecture.dto.LectureDto;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.lecture.mapper.LectureMapper;
import David.glass_time_studio.domain.lecture.service.LectureService;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/Booking")
@Slf4j
public class BookingController {

    private BookingMapper bookingMapper;
    private BookingService bookingService;
    private LectureService lectureService;
    private MemberService memberService;

    public BookingController(BookingMapper bookingMapper, BookingService bookingService,
                             LectureService lectureService, MemberService memberService){
        this.bookingMapper=bookingMapper;
        this.bookingService=bookingService;
        this.lectureService=lectureService;
        this.memberService = memberService;

    }
    // 예약 생성
    @PostMapping
    public ResponseEntity<?> postBooking(@RequestBody @Valid BookingDto.Post post, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try{
            log.info("lectureId: {}", post.getLectureId());
            log.info("requestDate: {}", post.getRequestDate());
            log.info("requestTime: {}", post.getRequestTime());
            log.info("bookerName: {}", post.getBookerName());
            log.info("peopleNumber: {}", post.getPeopleNumber());
            log.info("requestMessage: {}", post.getRequestMessage());
            log.info("mobile: {}", post.getMobile());
            log.info("memberId: {}", post.getMemberId());

            Long lectureId = post.getLectureId();
            Long memberId = post.getMemberId();

            Booking booking = bookingMapper.bookingDtoPostToBooking(post);
            booking.setLectureId(lectureId);
            log.info("수업 예약 요청의 수업번호: {}", booking.getLectureId());

            Member bookerMember = memberService.findMemberById(memberId);
            booking.setMember(bookerMember);
            log.info("수업 예약 요청의 회원번호: {}", booking.getMember().getMemberId());

            Lecture lecture = lectureService.findLecture(booking.getLectureId());
            log.info("수업 예약 요청의 수업 이름: {}", lecture.getLecture_Name());

            booking.setStatus("N");
            booking.setLectureName(lecture.getLecture_Name());

            Booking postedBooking = bookingService.makeNewBooking(booking);
            BookingDto.Response response = bookingMapper.bookingToBookingDtoResponse(postedBooking);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Expose-Headers", "Authorization");
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace(); // 콘솔에 상세 오류 로그 출력
            return new ResponseEntity<>("An Error occurred: "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 단일 예약 조회 =>(/bookingId) jsp 에서 사용

    // 전체 예약 조회
    @GetMapping("/all")
    public ResponseEntity findAllBookings(@Positive @RequestParam int page,
                                          @Positive @RequestParam int size){
        Page<Booking> bookings = bookingService.findAllBookings(page-1, size);
        PageInfo pageInfo = new PageInfo(
                bookings.getNumber(),
                bookings.getSize(),
                bookings.getTotalElements(),
                bookings.getTotalPages());
        List<Booking> bookingList = bookings.getContent();
        List<BookingDto.Response> responses = bookingMapper.bookingsToBookingDtoResponse(bookingList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo), HttpStatus.OK);
    }
    // 예약 변경
    @PatchMapping("/{bookingId}")
    public ResponseEntity patchBooking(@PathVariable("bookingId")@Positive Long bookingId,
                                       @RequestBody BookingDto.Patch patch){
        Booking booking = bookingMapper.bookingDtoPatchToBooking(patch);
        Long lectureId = patch.getLectureId();
        Lecture lecture = lectureService.findLecture(lectureId);
        String lectureName = lecture.getLecture_Name();

        log.info("bookingId : "+bookingId);
        log.info("lectureId : "+lectureId);
        log.info("lectureName : "+lectureName);
        booking.setLectureId(lectureId);
        booking.setLectureName(lectureName);

        log.info("setlectureId의 결과: "+booking.getLectureId());
        log.info("setLectureName의 결과: "+booking.getLectureName());

        Booking updatedBooking = bookingService.updateBooking(booking, bookingId);
        log.info("업데이트 booking의 lectureId: "+updatedBooking.getLectureId());
        log.info("업데이트 booking의 lectureName: "+updatedBooking.getLectureName());
        BookingDto.Response response = bookingMapper.bookingToBookingDtoResponse(updatedBooking);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity confirmBooking(@PathVariable("bookingId")@Positive Long bookingId){
        bookingService.confirmBook(bookingId);

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+bookingId+"'번 예약 확정이 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity cancelBooking(@PathVariable("bookingId")@Positive Long bookingId){
        bookingService.cancelBook(bookingId);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+bookingId+"'번 예약 취소가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookingDto.Response>> findMyBooking(@PathVariable("memberId")@Positive Long memberId){
        List<Booking> bookings = bookingService.findMyBooking(memberId);
        List<BookingDto.Response> response = bookings.stream()
                .map(bookingMapper::bookingToBookingDtoResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 회원번호 기준 예약 전체 조회
    @GetMapping("/all/{memberId}")
    public ResponseEntity findAllLecture(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size,
                                         @PathVariable("memberId")@Positive Long memberId){
        Page<Booking> bookings = bookingService.findAllBookingsByMemberId(page-1, size, memberId);

        PageInfo pageInfo = new PageInfo(
                bookings.getNumber(),
                bookings.getSize(),
                bookings.getTotalElements(),
                bookings.getTotalPages());
        List<Booking> bookingList = bookings.getContent();
        List<BookingDto.Response> responses = bookingMapper.bookingsToBookingDtoResponse(bookingList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo), HttpStatus.OK);
    }


    // 검색 for Y
    @GetMapping("/search_Y")
    public ResponseEntity<List<BookingDto.Response>> searchAnnouncements_Y(@RequestParam("keyword") String keyword){
        log.info("전달받은 검색어: "+keyword);
        List<Booking> bookings = bookingService.searchByName_Y(keyword);
        log.info("검색어로 조회한 데이터: "+bookings);
        List<BookingDto.Response> responses = bookings.stream()
                .map(bookingMapper::bookingToBookingDtoResponse)  // 각 객체를 개별적으로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    // 검색 for N
    @GetMapping("/search_N")
    public ResponseEntity<List<BookingDto.Response>> searchAnnouncements_N(@RequestParam("keyword") String keyword){
        log.info("전달받은 검색어: "+keyword);
        List<Booking> bookings = bookingService.searchByName_N(keyword);
        log.info("검색어로 조회한 데이터: "+bookings);
        List<BookingDto.Response> responses = bookings.stream()
                .map(bookingMapper::bookingToBookingDtoResponse)  // 각 객체를 개별적으로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    // 예약 취소
    @DeleteMapping("/{bookingId}")
    public ResponseEntity deleteBooking(@PathVariable("bookingId") @Positive Long bookingId){
        Booking booking = bookingService.findBooking(bookingId);
        bookingService.deleteBooking(booking);

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+booking.getBookingId()+"'번 예약 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
    @DeleteMapping("/{memberId}/{bookingId}")
    public ResponseEntity deleteBookingbyMember(@PathVariable("memberId") @Positive Long memberId,
                                                @PathVariable("bookingId") @Positive Long bookingId){
        Booking booking = bookingService.findBookingByMemberIdAndBookingId(memberId, bookingId);
        bookingService.deleteBooking(booking);

        String bookerName = booking.getBookerName();

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+bookerName+"'님의 "+booking.getBookingId()+"'번 예약 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
