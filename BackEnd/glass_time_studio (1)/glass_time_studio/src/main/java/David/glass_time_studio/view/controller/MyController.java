package David.glass_time_studio.view.controller;

import David.glass_time_studio.domain.announcement.dto.AnnouncementDto;
import David.glass_time_studio.domain.announcement.entity.Announcement;
import David.glass_time_studio.domain.announcement.mapper.AnnouncementMapper;
import David.glass_time_studio.domain.announcement.service.AnnouncementService;
import David.glass_time_studio.domain.booking.dto.BookingDto;
import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.booking.mapper.BookingMapper;
import David.glass_time_studio.domain.booking.service.BookingService;
import David.glass_time_studio.domain.lecture.dto.LectureDto;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.lecture.mapper.LectureMapper;
import David.glass_time_studio.domain.lecture.service.LectureService;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MyController {

    private MemberRepository memberRepository;
    private MemberService memberService;
    private AnnouncementService announcementService;
    private AnnouncementMapper announcementMapper;
    private LectureService lectureService;
    private LectureMapper lectureMapper;
    private BookingService bookingService;
    private BookingMapper bookingMapper;

    @Value("${app.api.endpoint}")
    private String apiEndPoint;

    public MyController(MemberRepository memberRepository,
                        MemberService memberService,
                        AnnouncementService announcementService,
                        AnnouncementMapper announcementMapper,
                        LectureService lectureService,
                        LectureMapper lectureMapper,
                        BookingService bookingService,
                        BookingMapper bookingMapper){
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.announcementService =announcementService;
        this.announcementMapper=announcementMapper;
        this.lectureService=lectureService;
        this.lectureMapper=lectureMapper;
        this.bookingService=bookingService;
        this.bookingMapper=bookingMapper;

    }
    @GetMapping("/main")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "index";
    }
    @GetMapping("/review")
    public String to_review(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/review/review";
    }
    @GetMapping("/mypage")
    public String to_mypage(Model model){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
        String email = String.valueOf(responseAttributes.get("email"));
        String name = String.valueOf(responseAttributes.get("name"));
        String mobile = String.valueOf(responseAttributes.get("mobile"));
        String birthday = String.valueOf(responseAttributes.get("birthday"));

        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        log.info("MyController-이름: "+name);
        log.info("MyController-이메일: "+email);
        log.info("MyController-휴대폰: "+mobile);
        log.info("MyController-생일: "+birthday);

        Member member = memberRepository.findMemberByEmail(email);
        if(member != null){
            model.addAttribute("member", member);
            log.info("생성한 member 이름: "+member.getMemberName());
            log.info("생성한 member 이메일: "+member.getEmail());
            log.info("생성한 member 휴대폰: "+member.getMobile());
            log.info("생성한 member 생일: "+member.getBirthday());
            log.info("생성한 member 아이디: "+member.getMemberId());
            boolean isAdmin = "ADMIN".equals(member.getPermit());
            model.addAttribute("isAdmin", isAdmin);
        }else{
            model.addAttribute("member",null);
            log.error("해당 email: "+email+" 을 가진 회원을 찾을 수 없습니다.");
        }
        return "layouts/login/myPage";
    }
    @GetMapping("/updateInfo")
    public String update_Info(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/login/updateInfo";
    }

    @GetMapping("/manager")
    public String managerPage(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/manager/manager";
    }

    @GetMapping("/photo")
    public String photoUpload(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/photo/photoView";
    }

}
