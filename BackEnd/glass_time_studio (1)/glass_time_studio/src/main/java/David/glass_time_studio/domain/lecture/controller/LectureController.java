package David.glass_time_studio.domain.lecture.controller;

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
