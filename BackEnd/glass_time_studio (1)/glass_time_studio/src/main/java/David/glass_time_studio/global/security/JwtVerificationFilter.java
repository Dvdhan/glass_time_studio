package David.glass_time_studio.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    //토큰 인코딩, 디코딩, 검증을 위한 DI
    private final JwtTokenizer jwtTokenizer;
    // 권한 설정을 위한 DI
    private final glassTimeStudioAuthorityUtils authorityUtils;
    // 토근을 Redis에서 관리하기 위한 DI
    private final TokenRedisRepository tokenRedisRepository;

    @Value("${mail.address.admin}")
    private String oauth2Login;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 glassTimeStudioAuthorityUtils authorityUtils,
                                 TokenRedisRepository tokenRedisRepository){
        this.jwtTokenizer=jwtTokenizer;
        this.authorityUtils=authorityUtils;
        this.tokenRedisRepository=tokenRedisRepository;
    }
    // JWT 토큰 검증 과정을 위한 메서드

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // request header 로부터 "Authorization"이라는 내용을 담는 객체 생성
        String authorizationHeader = request.getHeader("Authorization");
        log.info(oauth2Login);
        // 만약 해당 객체가 null이 아니고, "Bearer "으로 시작한다면,
        // "Bearer "을 제외한 나머지 부분을 token 객체로 생성
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.replace("Bearer ","");
            // Redis Repository를 통해 해당 토큰이 사용됬거나,
            // 발급된 token과 Redis에서 조회한 보관중인 AccessToken과 같은 값이라면
            if (tokenRedisRepository.isAccessTokenUsed(token) || token.equals(tokenRedisRepository.getAccessToken(token))){
                // BadCredentialException 예외 처리
                throw new BadCredentialsException("Invalid Access Token");
            }
            try {
                // header에서 "Bearer "을 제외한 값 token을 검증하여 claims 객체에 할당
                Map<String, Object> claims = verifyJws(token);
                // verifyJws 메서드를 통해 claims에 할당된 token 값, key 값을
                // SecurityContextHolder에 셋팅
                setAuthenticationToContext(claims);
            } catch (SignatureException se) {
                request.setAttribute("exception", se);
            }
            // 토큰이 기간 만료일 경우, request header에서 Refresh를 가지고옴
            catch (ExpiredJwtException ee){
                String refreshToken = request.getHeader("Refresh");
                // 가지고온 refreshToken을 검증하여 token과 key를 검증
                try{
                    verifyJws(refreshToken);
                    // 검증 결과, refreshToken도 만료되었다면, 로그인 URL을 반환
                } catch (ExpiredJwtException e){
                    System.out.println("before redirect: "+oauth2Login);
                    response.sendRedirect(oauth2Login);
                    System.out.println("after redirect: "+oauth2Login);
                    return;
                }
                // Redis에서 조회하여 해당 refreshToken이 사용되었거나, 발급 이력이 있다면
                if(tokenRedisRepository.isRefreshTokenUsed(refreshToken) ||
                refreshToken.equals(tokenRedisRepository.getRefreshToken(refreshToken))){
                    // 사용 불가능한 토큰이라는 메시지 전달
                    sendCustomErrorResponse(response, "", "", "Invalid Refresh Token",
                            HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                // 위에서 진행한 AccessToken이 만료시 Refresh 토큰의 검증에 이상이 없을 경우 진행 부분
                // 새로운 accessToken을 위해 refreshToken 사용
                String newAccessToken = jwtTokenizer.regenerateAccessToken(refreshToken);

                // 1번째 refreshToken의 payload(사용자 정보)와 Base64 형식으로 인코딩된 secretKey를 Jws<Claims>에 담음
                Jws<Claims> refreshTokenClaims = jwtTokenizer.getClaims(refreshToken,
                        jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                // 새로 생성될 RefreshToken의 만료시간을 application.yml로부터 읽어서 Date 객체에 할당.
                Date refreshTokenExpiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());

                // 새로운 위에 생성한 refreshTokenClaims와 Date 객체를 사용해 새로운 Refresh 토큰 생성.
                String newRefreshToken = jwtTokenizer.generateRefreshToken(refreshTokenClaims.getBody().getSubject(),
                        refreshTokenExpiration, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                // 각 토큰들이 Redis 안에서 보관되는 기간을 JwtTokenizer 클래스의 필드만큼 지정. (결국 yml 설정만큼)
                long accessTokenDuration = jwtTokenizer.getAccessTokenExpirationMinutes();
                long refreshTokenDuration = jwtTokenizer.getRefreshTokenExpirationMinutes();

                // 새로 생성된 AccessToken과 RefreshToken을 application에 작성된 토큰 보관기간과 동일하게 Redis에 저장.
                saveNewAccessTokenAndRefreshToken(newAccessToken, newRefreshToken, refreshToken,
                        accessTokenDuration, refreshTokenDuration, TimeUnit.MINUTES);
                // 새로 생성된 nmewAccessToken과 newRefreshToken을 response에 할당하고 메시지를 전달.
                sendCustomErrorResponse(response, newAccessToken, newRefreshToken,
                        "New AccessToken and RefreshToken generated", HttpServletResponse.SC_UNAUTHORIZED);
                return;
                // 위에서 제시했던 예외들 (SignatureException or ExpiredJwtException) 말고
                // 예상 범위 밖의 어떠한 예외가 발생할 경우 대비하여 Genereic Exception  작성해둠.
            } catch (Exception e){
                request.setAttribute("exception", e);
            }
        }
        // doFilterInternal 메서드의 정상처리 이후 다음 Security Filter로 진행 코드
        filterChain.doFilter(request, response);
    }

    private void sendCustomErrorResponse(HttpServletResponse response,
                                         String newAccessToken,
                                         String newRefreshToken,
                                         String message,
                                         int status) throws IOException{
        // 전달받은 Https Status code를 response에 셋팅
        response.setStatus(status);
        // 전달받은 newAccessToken 앞에 문자열을 추가하여 response header에 지정.
        response.setHeader("Authorization", "Bearer "+newAccessToken);
        // 전달받은 newRefreshToken 앞에 문자열 추가하여 response header에 지정.
        response.setHeader("Refresh", newRefreshToken);
        // response 타입을 설정하여 response body의 출력 형태를 지정.
        response.setContentType("application/json");

        // response body 새로 생성
        Map<String, String> responseBody = new HashMap<>();
        // message 할당
        responseBody.put("error", message);
        // ObjectMapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();
        // ResponseBody 타입을 JSON-format string 으로 변경
        String json = mapper.writeValueAsString(responseBody);
        // JSON-format string을 response body에 작성
        response.getWriter().write(json);
        // response 안에 포함된 내용들이 client에 출력되도록 flush 처리.
        response.getWriter().flush();
        // response 에 더이상 작성될 내용이 없도록 출력 스트림 연결 종료
        response.getWriter().close();
    }
    private void saveNewAccessTokenAndRefreshToken(String newAccessToken,
                                                   String newRefreshToken,
                                                   String usedRefreshToken,
                                                   long accessTokenDuration,
                                                   long refreshTokenDuration,
                                                   TimeUnit timeUnit){

        // Redis에 새로 생성된 AccessToken과 RefreshToken을 설정해둔 유효 기간동안 보관
        tokenRedisRepository.saveNewAccessTokenAndRefreshToken(newAccessToken, newRefreshToken,
                accessTokenDuration, refreshTokenDuration, timeUnit);
        // Redis에서 new AccessToken을 생성할 때 사용한 usedRefreshToken을
        // 최초 생성될 때 지정된 RefreshToken의 유효 기간 동안 보관
        tokenRedisRepository.saveUsedRefreshToken(usedRefreshToken,
                jwtTokenizer.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    // request header에서 "Authorization"이 null 인지, "Bearer "로 시작하지 않는지 확인
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    // 새로 생성한 AccessToken과 이를 위해 사용된 RefreshToken을 저장하는 메서드
    private void saveNewAccessToken(String newAccessToken, String usedRefreshToken,
                                    long duration, TimeUnit timeUnit){
        tokenRedisRepository.saveUsedAccessToken(newAccessToken, duration, timeUnit);
        tokenRedisRepository.saveUsedRefreshToken(usedRefreshToken, 10, TimeUnit.MINUTES);
    }

    // jws를 인코딩된 key를 활용하여 검증하고 map 형태의 claims(payload)를 반환한다
    public Map<String, Object> verifyJws(String jws){
        // SecretKey를 Base64형식으로 인코딩 처리
        String base64EncodedSecretKey =
                jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        // getClaims() 메서드는 jws(Json Web Signature), Base64 인코딩된 key 검증하고
        // Jws<Claims> 객체를 반환. getBody() 메서드는 Claim을 Map 타입으로 추출.
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims){
        // ContextHolder에 저장되는 UsernamePasswordAuthenticationToken의 Principal을 memberId로 변환
        // memberId는 Object 타입이므로, String 타입으로 형변환하여 claims 객체에 담아준다.
        // Controller를 통해 들어오는 요청에서 같은 USER 권한이더라도 요청에 담긴 member 객체의 memberId로
        // 요청자를 구분하여 resource에 접근하려는 주체를 구분할 수 있다. + Request URL의 간단함.

        // 토큰의 claims(payload)에서 email을 가져온다.
//        String memberId = (String) claims.get("memberId");
        String email = (String) claims.get("email");
        // 토큰의 claims(payload)에서 role을 가져와서 createAuthorities 메서드를 통해 권한을 생성
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));

        // Principal : memberId, password : null, role을 가져와 생성한 authorities로 Authentication 객체 생성.
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        // SecurityContextHolder 에서 보관하는 Context로써 새로 생성한 authenticaiton 객체를 저장함.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
