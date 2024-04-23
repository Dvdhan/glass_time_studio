package David.glass_time_studio.domain.member.controller;

import David.glass_time_studio.domain.member.dto.MemberDto;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.mapper.MemberMapperImpl;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;

    private MemberService memberService;
    private MemberRepository memberRepository;
    private OAuth2AuthorizedClientService authorizedClientService;
    private OAuth2AuthorizedClientManager authorizedClientManager;
    private MemberMapperImpl memberMapper;

    public MemberController(MemberService memberService,
                            MemberRepository memberRepository,
                            OAuth2AuthorizedClientService authorizedClientService,
                            OAuth2AuthorizedClientManager authorizedClientManager,
                            MemberMapperImpl memberMapper){
        this.memberService=memberService;
        this.memberRepository=memberRepository;
        this.authorizedClientService=authorizedClientService;
        this.authorizedClientManager=authorizedClientManager;
        this.memberMapper=memberMapper;
    }
    @GetMapping("/login")
    public void getLogin(HttpServletResponse response) throws IOException{
        String aa = "http://localhost:8080/oauth2/authorization/naver";
//        response.sendRedirect(url);
        response.sendRedirect(aa);
    }
    @PostMapping("/update")
    public void updateUser(@RequestParam("email") String email,
                             @RequestParam("mobile") String mobile,
                             @RequestParam("memberId") Long memberId,
                             HttpServletResponse response) throws IOException {
        log.info("멤버아이디: "+memberId);
        log.info("멤버이메일: "+email);
        log.info("멤버휴대폰: "+mobile);
        if(memberId != null){
            Member target = memberService.findMemberById(memberId);
            if(email != target.getEmail()){
                target.setEmail(email);
                memberRepository.save(target);
            }
            if(mobile != target.getMobile()){
                target.setMobile(mobile);
                memberRepository.save(target);
            }
        }
        response.sendRedirect("http://localhost:8080/main");
    }
}
