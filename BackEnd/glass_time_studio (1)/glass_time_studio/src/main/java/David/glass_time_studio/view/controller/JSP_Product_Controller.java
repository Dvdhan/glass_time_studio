package David.glass_time_studio.view.controller;

import David.glass_time_studio.domain.announcement.mapper.AnnouncementMapper;
import David.glass_time_studio.domain.announcement.service.AnnouncementService;
import David.glass_time_studio.domain.booking.mapper.BookingMapper;
import David.glass_time_studio.domain.booking.service.BookingService;
import David.glass_time_studio.domain.lecture.mapper.LectureMapper;
import David.glass_time_studio.domain.lecture.service.LectureService;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@Slf4j
public class JSP_Product_Controller {
    private MemberRepository memberRepository;
    private MemberService memberService;
    private AnnouncementService announcementService;
    private AnnouncementMapper announcementMapper;
    private LectureService lectureService;
    private LectureMapper lectureMapper;
    private BookingService bookingService;
    private BookingMapper bookingMapper;


    public JSP_Product_Controller(MemberRepository memberRepository,
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

    @GetMapping("/product")
    public String to_product(Model model, HttpServletRequest request){
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
        return "layouts/product/product";
    }

    @GetMapping("/product_detail")
    public String product_detail(Model model, HttpServletRequest request){
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
        return "layouts/product/product_detail";
    }
}
