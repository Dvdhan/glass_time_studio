package David.glass_time_studio.global.security.OAuth2;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.global.security.JWT.JwtTokenizer;
import David.glass_time_studio.global.security.JWT.glassTimeStudioAuthorityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 인증 성공 이후 실행되는 핸들러.
// JWT 토큰을 생성하고 최종적으로 어떤 URL로 리다이렉트할 지 결정.
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final glassTimeStudioAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공");
        // 로그인에 성공한 authentication 객체의 principal("member_Id")을 oAuth2User로 지정.
//        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authToken.getAuthorizedClientRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");

        String email = String.valueOf(responseAttributes.get("email"));
        String name = String.valueOf(responseAttributes.get("name"));
        String mobile = String.valueOf(responseAttributes.get("mobile"));
        String birthday = String.valueOf(responseAttributes.get("birthday"));
        String age = String.valueOf(responseAttributes.get("age"));

//        saveMember(email);

        // email을 토대로 Role을 생성
        List<String> authorities = authorityUtils.createRoles(email);
        log.info("Role: "+authorities);
        String permit = authorityUtils.createPermit(email);
        log.info("성공이후 생성되는 permit: "+permit);

        // email을 토대로 DB에서 member 찾음.
        Member foundMember = memberRepository.findMemberByEmail(email);

        // 찾아지는 객체가 없다면, 새로 생성 후 name, oauthType 할당.
        if(foundMember == null){
            Member member = new Member(email);
            member.setMemberName(name);
            member.setOauthType(registrationId);
//            member.setPermit(authorities);
            member.setPermit(permit);
            member.setMobile(mobile);
            member.setBirthday(birthday);
            member.setAge(age);
            foundMember = memberRepository.save(member);
        }
        // 찾아지는 멤버가 있다면, name 할당 후 DB 저장
        else {
            int updated = 0;
            if(foundMember.getMemberName() != name){
                foundMember.setMemberName(name);
                updated+=1;
            }
            if(foundMember.getOauthType() != registrationId){
                foundMember.setOauthType(registrationId);
                updated+=1;
            }
            if(foundMember.getAge() != age){
                foundMember.setAge(age);
                updated+=1;
            }
            if(foundMember.getMobile() != mobile){
                foundMember.setMobile(mobile);
                updated+=1;
            }
            if(foundMember.getBirthday() != birthday){
                foundMember.setBirthday(birthday);
                updated+=1;
            }
            if(updated != 0){
                memberRepository.save(foundMember);
            }
        }

        // DB에서 조회된 회원 객체와 email을 기준으로 생성한 권한을 부여하여 accessToken 생성.
        String accessToken = delegateAccessToken(foundMember, authorities);
//        String accessToken = delegateAccessToken(foundMember, permit);
        log.info("엑세스 토큰 생성 :"+accessToken);

        // 회원의 name을 할당하여 RefreshToken을 생성함.
        String refreshToken = delegateRefreshToken(name);
        log.info("리프레시 토큰 : "+refreshToken);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setSecure(false); // HTTPS 사용 시,
        accessTokenCookie.setPath("/"); // 모든 경로에서 쿠키 유효
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        // response에 (accessToken, refreshToken을 response Header로 추가)
        response.addHeader(accessToken, refreshToken);
        // response의 Header에 "Authorization" 항목에 "Bearer "+accessToken 할당
        response.setHeader("Authorization", "Bearer "+accessToken);
        // response의 Header에 "Refresh" 항목에 refreshToken 할당.
        response.setHeader("Refresh", refreshToken);

        log.info("response: "+response);

        // 사용자에게 makeRedirectUrl 메서드를 활용하여 endpoint + accessToken + RefreshToken 보냄.
        String redirectUrl = "/mypage";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
    private String delegateAccessToken(Member member, List<String> authorities){

        // Token 에서 확인되는 payload를 보관하기 위한 claims 생성
        Map<String, Object> claims = new HashMap<>();

        // Object 자리인 Value를 Long 타입인 member_Id를 담기위해 String 으로 감싸서 지정
        claims.put("memberId", String.valueOf(member.getMemberId()));
        claims.put("memberName", member.getMemberName());
//        claims.put("roles", authorities);
        claims.put("authorities", authorities);

        // member_Id를 String 형태인 subject에 할당.
        String subject = String.valueOf(member.getMemberId());

        // accessToken의 유효기간을 yml로부터 가져와 Date 객체에 할당.
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        // secretKey를 Base64 형식으로 인코딩하여 String 객체에 할당
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        // 위에 정의한 claims, subject(member_Id), expiration, key를 전달하여 AccessKey 생성.
        try{
            String accessToken = jwtTokenizer.generateAccessToken(
                    claims, subject, expiration, base64EncodedSecretKey);
            log.info("호출6");
            return accessToken;
        } catch (Exception e){
            log.error("AccessToken 생성 중 오류 발생", e);
            throw new RuntimeException("AccessToken 생성 중 오류", e);
        }
    }
//    private String delegateAccessToken(Member member, String permit){
//
//        // Token 에서 확인되는 payload를 보관하기 위한 claims 생성
//        Map<String, Object> claims = new HashMap<>();
//
//        // Object 자리인 Value를 Long 타입인 member_Id를 담기위해 String 으로 감싸서 지정
//        claims.put("memberId", String.valueOf(member.getMemberId()));
//        claims.put("memberName", member.getMemberName());
//    //        claims.put("roles", authorities);
//        claims.put("authorities", permit);
//
//        // member_Id를 String 형태인 subject에 할당.
//        String subject = String.valueOf(member.getMemberId());
//
//        // accessToken의 유효기간을 yml로부터 가져와 Date 객체에 할당.
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
//
//        // secretKey를 Base64 형식으로 인코딩하여 String 객체에 할당
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//
//        // 위에 정의한 claims, subject(member_Id), expiration, key를 전달하여 AccessKey 생성.
//        try{
//            String accessToken = jwtTokenizer.generateAccessToken(
//                    claims, subject, expiration, base64EncodedSecretKey);
//            log.info("호출6");
//            return accessToken;
//        } catch (Exception e){
//            log.error("AccessToken 생성 중 오류 발생", e);
//            throw new RuntimeException("AccessToken 생성 중 오류", e);
//        }
//    }
    private String delegateRefreshToken(String userName){
        // 파라미터로 주어진 username을 subject로 할당
        String subject = userName;
        // 유효 기간을 application.yml에 설정한 시간으로 가져와서 Date 객체에 담음
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        // Base64 형식으로 secretKey를 인코딩
        String base64EncodedSecretKey =
                jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        // subject, expiration, base64EncodedKey로 RefreshToken 생성함.
        String refreshToken = jwtTokenizer.generateRefreshToken(
                subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
