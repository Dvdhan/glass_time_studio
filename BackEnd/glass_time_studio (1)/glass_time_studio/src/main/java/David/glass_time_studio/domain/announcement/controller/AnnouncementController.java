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
@RequestMapping("/Announcement")
@Slf4j
public class AnnouncementController {

    private AnnouncementService announcementService;
    private AnnouncementMapper announcementMapper;

    public AnnouncementController(AnnouncementService announcementService, AnnouncementMapper announcementMapper){
        this.announcementService=announcementService;
        this.announcementMapper=announcementMapper;
    }

    // 공지사항 추가
    @Transactional
    @PostMapping
    public ResponseEntity postAnnouncement (@RequestBody @Valid AnnouncementDto.Post post){
        Announcement announcement = announcementMapper.announcementDtoPostToAnnouncement(post);
        Announcement postedAnnouncement = announcementService.postAnnouncement(announcement);
        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(postedAnnouncement);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }
    // 공지사항 수정
    @PatchMapping("/{announcement_Id}")
    public ResponseEntity patchAnnouncement(@PathVariable("announcement_Id")@Positive Long announcement_id,
                                             @RequestBody AnnouncementDto.Patch patch){
        Announcement announcement = announcementMapper.announcementDtoPatchToAnnouncement(patch);
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement, announcement_id);
        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(updatedAnnouncement);
        return new ResponseEntity(response, HttpStatus.OK);

    }
    // 공지사항 단일 조회
    @GetMapping("/{announcement_Id}")
    public ResponseEntity findAnnouncement(@PathVariable("announcement_Id")@Positive Long announcement_id){
        Announcement announcement = announcementService.findAnnouncement(announcement_id);
        AnnouncementDto.Response response = announcementMapper.announcementToAnnouncementDtoResponse(announcement);
        return new ResponseEntity(response, HttpStatus.OK);
    }
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
        responseMessage.put("message", "요청하신 '"+announcement.getAnnouncement_Title()+"' 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
