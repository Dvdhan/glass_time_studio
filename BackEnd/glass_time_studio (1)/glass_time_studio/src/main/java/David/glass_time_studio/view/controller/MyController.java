package David.glass_time_studio.view.controller;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@Slf4j
public class MyController {

    private MemberRepository memberRepository;
    private MemberService memberService;

    public MyController(MemberRepository memberRepository, MemberService memberService){
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @GetMapping("/main")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/announcement")
    public String evenAnnouncement(){
        return "layouts/announcement/event_announcement";
    }
    @GetMapping("/class")
    public String load_class(){
        return "layouts/class/class";
    }
    @GetMapping("/product")
    public String to_product(){
        return "layouts/product/product";
    }
    @GetMapping("/review")
    public String to_review(){
        return "layouts/review/review";
    }
    @GetMapping("/reservation")
    public String to_reservation(){
        return "layouts/reservation/reservation";
    }
    @GetMapping("/product_detail")
    public String product_detail(){
        return "layouts/product/product_detail";
    }
    @GetMapping("/mypage")
    public String to_mypage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
        String email = String.valueOf(responseAttributes.get("email"));
        String name = String.valueOf(responseAttributes.get("name"));
        String mobile = String.valueOf(responseAttributes.get("mobile"));
        String birthday = String.valueOf(responseAttributes.get("birthday"));

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
        }else{
            log.error("해당 email: "+email+" 을 가진 회원을 찾을 수 없습니다.");
        }
        return "layouts/login/myPage";
    }
    @GetMapping("/updateInfo")
    public String update_Info(){
        return "layouts/login/updateInfo";
    }
}
