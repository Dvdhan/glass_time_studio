package David.glass_time_studio.domain.lecture.controller;

import David.glass_time_studio.domain.announcement.dto.AnnouncementDto;
import David.glass_time_studio.domain.announcement.entity.Announcement;
import David.glass_time_studio.domain.lecture.dto.LectureDto;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.lecture.mapper.LectureMapper;
import David.glass_time_studio.domain.lecture.service.LectureService;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/lecture")
@Slf4j
public class LectureController {

    @Autowired
    private LectureMapper lectureMapper;

    @Autowired
    private LectureService lectureService;

    // 수업 등록
    @Transactional
    @PostMapping
    public ResponseEntity postLecture(@RequestBody @Valid LectureDto.Post post){
        Lecture lecture = lectureMapper.lectureDtoPostToLecture(post);
        log.info("전달받은 클래스 제목: "+lecture.getLecture_Name());
        log.info("전달받은 클래스 기간: "+lecture.getLecture_Period());
        log.info("전달받은 클래스 설명: "+lecture.getLecture_Description());
        log.info("전달받은 클래스 가격: "+lecture.getLecture_Price());
        log.info("전달받은 클래스 상태: "+lecture.getStatus());
        Lecture postedLecture = lectureService.postLecture(lecture);
        LectureDto.Response response = lectureMapper.lectureToLectureDtoResponse(postedLecture);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }

    // 수업 수정
    @PatchMapping("/{lecture_Id}")
    public ResponseEntity updateLecture(@PathVariable("lecture_Id")@Positive Long lecture_id,
                                        @RequestBody LectureDto.Patch patch){
        Lecture lecture = lectureMapper.lectureDtoPatchToLecture(patch);
        Lecture updatedLecture = lectureService.updateLecture(lecture, lecture_id);
        LectureDto.Response response = lectureMapper.lectureToLectureDtoResponse(updatedLecture);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/all_available")
    public ResponseEntity findAvailable(){
        List<Lecture> lectures = lectureService.getAllAvailableLectures();
        List<LectureDto.Response> responses = lectureMapper.lecturesToLectureDtoResponse(lectures);
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 수업 전체 조회
    @GetMapping("/all")
    public ResponseEntity findAllLecture(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size){
        Page<Lecture> lectures = lectureService.findAllLecture(page-1, size);

        PageInfo pageInfo = new PageInfo(
                lectures.getNumber(),
                lectures.getSize(),
                lectures.getTotalElements(),
                lectures.getTotalPages());
        List<Lecture> lectureList = lectures.getContent();
        List<LectureDto.Response> responses = lectureMapper.lecturesToLectureDtoResponse(lectureList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo), HttpStatus.OK);
    }

//    @GetMapping("/{lecture_Id}")
//    public ResponseEntity findOneLecture(@PathVariable("lecture_Id")@Positive Long lecture_Id){
//        Lecture lecture = lectureService.findLecture(lecture_Id);
//        LectureDto.Response response = lectureMapper.lectureToLectureDtoResponse(lecture);
//        return new ResponseEntity(response, HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<LectureDto.Response>> searchAnnouncements(@RequestParam("keyword") String keyword){
        log.info("전달받은 검색어: "+keyword);
        List<Lecture> lectures = lectureService.searchLecturesByTitle(keyword);
        log.info("검색어로 조회한 데이터: "+lectures);
        List<LectureDto.Response> responses = lectures.stream()
                .map(lectureMapper::lectureToLectureDtoResponse)  // 각 객체를 개별적으로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 수업 삭제
    @DeleteMapping("/{lecture_Id}")
    public ResponseEntity deleteLecture(@PathVariable("lecture_Id")@Positive Long lecture_Id){
        Lecture lecture = lectureService.findLecture(lecture_Id);
        lectureService.deleteLecture(lecture);

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+lecture.getLecture_Name()+"' 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
