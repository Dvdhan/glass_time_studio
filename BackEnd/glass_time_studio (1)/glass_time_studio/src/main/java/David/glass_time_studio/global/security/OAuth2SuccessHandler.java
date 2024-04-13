package David.glass_time_studio.global.security;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final glassTimeStudioAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

        OAuth2User oAuth2User1 = oAuth2AuthenticationToken.getPrincipal();

        // 인증에 성공하면 OAuth2AuthorizedClient 객체를 통해 Spring Boot 에서 자동으로
        // Authorization code로 accessToken을 만든다.
        OAuth2AuthorizedClient authorizedClient =
                oAuth2AuthorizedClientService.loadAuthorizedClient(
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                        oAuth2AuthenticationToken.getName());

        String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        String name = oAuth2AuthenticationToken.getName();
        String naverAccessToken = authorizedClient.getAccessToken().getTokenValue();

        // 로그인에 성공한 authentication 객체의 principal("member_Id")을 oAuth2User로 지정.
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        // email 할당.
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        // email을 토대로 Role을 생성
        List<String> authorities = authorityUtils.createRoles(email);

        // 가져온 email로 새로운 회원 객체를 만들어 DB에 저장.
        saveMember(email);
        // email을 토대로 DB에서 회원 객체 할당
        Member member = memberRepository.findMemberByEmail(email);

        String userName = null;
        // DB에 주어진 email을 가진 회원이 있다면 userName을 그 회원의 name으로 할당.
        if (member != null){
            userName = member.getMember_Name();
        }

        // DB에서 조회된 회원 객체와 email을 기준으로 생성한 권한을 부여하여 accessToken 생성.
        String accessToken = delegateAccessToken(member, authorities);

        // 회원의 name을 할당하여 RefreshToken을 생성함.
        String refreshToken = delegateRefreshToken(userName);

        // response에 (accessToken, refreshToken을 response Header로 추가)
        response.addHeader(accessToken, refreshToken);
        // response의 Header에 "Authorization" 항목에 "Bearer "+accessToken 할당
        response.setHeader("Authorization", "Bearer "+accessToken);
        // response의 Header에 "Refresh" 항목에 refreshToken 할당.
        response.setHeader("Refresh", refreshToken);

        // 사용자에게 makeRedirectUrl 메서드를 활용하여 endpoint + accessToken + RefreshToken 보냄.
        getRedirectStrategy().sendRedirect(request, response, makeRedirectUrl(accessToken, refreshToken));
        System.out.println("naverAccessToken: "+naverAccessToken);
        System.out.println("registrationId: "+registrationId);
        System.out.println("name: "+name);
    }
    private String makeRedirectUrl(String accessToken, String refreshToken){
        // UriComponentsBuilder를 사용하여, scheme+host+port+path 형식으로 구성.
        return UriComponentsBuilder.fromUriString("http://localhost:8080/mypage")
                .queryParam("Authorization", accessToken)
                .queryParam("Refresh", refreshToken)
                .build().toUriString();
    }

    // email을 기준으로 새로운 회원 객체를 생성하고 DB에 회원 객체 저장
    // OAuth2 인증에 성공한 뒤 사용자의 이메일을 저장하는데 사용함.
    private void saveMember(String email){
        Member member = new Member(email);
        memberService.createMember(member);
    }

    private String delegateAccessToken(Member member, List<String> authorities){
        // Token 에서 확인되는 payload를 보관하기 위한 claims 생성
        Map<String, Object> claims = new HashMap<>();

        // Object 자리인 Value를 Long 타입인 member_Id를 담기위해 String 으로 감싸서 지정
        claims.put("member_Id", String.valueOf(member.getMember_Id()));
        claims.put("member_Name", member.getMember_Name());
        claims.put("roles", authorities);

        // member_Id를 String 형태인 subject에 할당.
        String subject = String.valueOf(member.getMember_Id());

        // accessToken의 유효기간을 yml로부터 가져와 Date 객체에 할당.
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        // secretKey를 Base64 형식으로 인코딩하여 String 객체에 할당
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        // 위에 정의한 claims, subject(member_Id), expiration, key를 전달하여 AccessKey 생성.
        String accessToken = jwtTokenizer.generateAccessToken(
                claims, subject, expiration, base64EncodedSecretKey);
        System.out.println("accessToken's member_Id: "+subject);
        return accessToken;
    }

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
