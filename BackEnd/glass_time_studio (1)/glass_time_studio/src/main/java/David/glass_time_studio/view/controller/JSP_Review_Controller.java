package David.glass_time_studio.view.controller;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class JSP_Review_Controller {

    @Value("${app.api.endpoint}")
    private String apiEndPoint;

    private MemberRepository memberRepository;

    public JSP_Review_Controller(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    // 메뉴 - 수강생 후기 버튼 (전체 조회)
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

    @GetMapping("/review/write")
    public String write_review(Model model, HttpServletRequest request){
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
        return "layouts/review/writeReview";
    }


}
