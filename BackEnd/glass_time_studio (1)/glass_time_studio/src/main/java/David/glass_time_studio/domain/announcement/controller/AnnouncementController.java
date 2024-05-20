package David.glass_time_studio.domain.announcement.controller;

import David.glass_time_studio.domain.announcement.dto.AnnouncementDto;
import David.glass_time_studio.domain.announcement.entity.Announcement;
import David.glass_time_studio.domain.announcement.mapper.AnnouncementMapper;
import David.glass_time_studio.domain.announcement.service.AnnouncementService;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.asm.IModelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/Announcement")
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    public AnnouncementController(AnnouncementService announcementService, AnnouncementMapper announcementMapper){
        this.announcementService=announcementService;
        this.announcementMapper=announcementMapper;
    }

    // 공지사항 추가
//    @Transactional
//    @PostMapping
//    public ResponseEntity postAnnouncement (@RequestBody @Valid AnnouncementDto.Post post){
//        Announcement announcement = announcementMapper.announcementDtoPostToAnnouncement(post);
//        Announcement postedAnnouncement = announcementService.postAnnouncement(announcement);
//        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(postedAnnouncement);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Expose-Headers", "Authorization");
//        return new ResponseEntity(response, headers, HttpStatus.CREATED);
//    }
    @Transactional
    @PostMapping
    public ResponseEntity<?> postAnnouncement(@RequestBody @Valid AnnouncementDto.Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            Announcement announcement = announcementMapper.announcementDtoPostToAnnouncement(post);
            Announcement postedAnnouncement = announcementService.postAnnouncement(announcement);
            AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(postedAnnouncement);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Expose-Headers", "Authorization");
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();  // 콘솔에 상세 오류 로그 출력
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 공지사항 수정
    @PatchMapping("/{announcement_Id}")
    public ResponseEntity patchAnnouncement(@PathVariable("announcement_Id")@Positive Long announcement_id,
                                             @RequestBody AnnouncementDto.Patch patch){
        log.info("전달받은 수정 id: "+announcement_id);
        Announcement announcement = announcementMapper.announcementDtoPatchToAnnouncement(patch);
        log.info("받은 JSON 데이터: "+patch);
        log.info("전달받은 수정 타이틀: "+announcement.getAnnouncement_Title());
        log.info("전달받은 수정 바디: "+announcement.getAnnouncement_Content());
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement, announcement_id);
        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(updatedAnnouncement);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnnouncementDto.Response>> searchAnnouncements(@RequestParam("keyword") String keyword){
        log.info("전달받은 검색어: "+keyword);
        List<Announcement> announcements = announcementService.searchAnnouncementsByTitle(keyword);
        log.info("검색어로 조회한 데이터: "+announcements);
        log.info("검색어로 조회한 데이터의 id"+announcements.toString());
        List<AnnouncementDto.Response> responses = announcements.stream()
                .map(announcementMapper::announcementToAnnouncementDtoResponse)  // 각 객체를 개별적으로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 공지사항 단일 조회
//    @GetMapping("/{announcement_Id}")
//    public ResponseEntity findAnnouncement(@PathVariable("announcement_Id")@Positive Long announcement_id){
//        Announcement announcement = announcementService.findAnnouncement(announcement_id);
//        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(announcement);
//
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
//    @GetMapping("/{announcement_Id}")
//    public String findAnnouncement(@PathVariable("announcement_Id")@Positive Long announcement_id,
//                                           Model model){
//        Announcement announcement = announcementService.findAnnouncement(announcement_id);
//        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(announcement);
//
//        model.addAttribute("announcement", response);
//
//        return "layouts/announcement/detail";
//    }
    // 공지사항 전체 조회
    @GetMapping("/all")
    public ResponseEntity findAllAnnouncement(@Positive @RequestParam int page,
                                              @Positive @RequestParam int size){
        Page<Announcement> announcements = announcementService.findAllAnnouncement(page-1, size);

        PageInfo pageInfo = new PageInfo(
                announcements.getNumber(),
                announcements.getSize(),
                announcements.getTotalElements(),
                announcements.getTotalPages());
        List<Announcement> announcementList = announcements.getContent();
        List<AnnouncementDto.Response> responses = announcementMapper.announcementsToAnnouncementDtoResponse(announcementList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 공지사항 삭제
    @DeleteMapping("/{announcement_Id}")
    public ResponseEntity deleteAnnouncement(@PathVariable("announcement_Id") @Positive long announcement_id){
        Announcement announcement = announcementService.findAnnouncement(announcement_id);
        announcementService.deleteAnnouncement(announcement);

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "요청하신 '"+announcement.getAnnouncement_Id()+"'번 공지글 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
