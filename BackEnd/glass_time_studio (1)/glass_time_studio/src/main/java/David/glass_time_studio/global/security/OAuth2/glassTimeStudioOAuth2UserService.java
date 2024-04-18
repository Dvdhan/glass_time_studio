package David.glass_time_studio.global.security.OAuth2;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;

// OAuth2 로그인 과정에서 사용자 정보를 로드할 때 호출.
// 사용자의 기본적인 프로필을 처리하며, 첫 로그인 시 DB에 정보를 저장, 업데이트하는 역할.
@Component
public class glassTimeStudioOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    public glassTimeStudioOAuth2UserService(MemberService memberService){
        this.memberService=memberService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 인증에 성공한 유저의 이메일, 이름과 같은 사용자 속성들을 OAuth2User 객체 oAuth2User에 담음.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String member_Name = oAuth2User.getAttribute("name");
        String oauth2Type = userRequest.getClientRegistration().getRegistrationId();
        Date birthday = oAuth2User.getAttribute("birthday");

        // OAuth2 인증에 성공한 유저의 이메일을 기준으로 DB에 저장된 회원이 있는지 조회
        Member member = memberService.findMemberByEmail(email);

        // 만약 회원 객체가 없다면, 이메일을 생성자로 주어 새로운 회원 객체 생성
        // 새로 생성한 회원 객체에 oAuth2에 보관한 정보(email, member_Name, oauth2Type)을 넣어줌.
//        if(member == null){
//            member = new Member(email);
//            member.setMember_Name(member_Name);
//            member.setOauthType(oauth2Type);
//            member.setBirthday(birthday);
//            memberService.createMember(member);
//        }
//        // 이메일 기준으로 DB에서 회원 객체가 조회된다면,
//        // 해당 객체에 member_Name, oauth2Type, birthday 를 할당
//        else{
//            try{
//                memberService.updateMember(member);
//            } catch(Exception e){
//                throw new RuntimeException(e);
//            }
//            return oAuth2User;
//        }
        return oAuth2User;
    }
}
