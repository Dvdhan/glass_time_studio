package David.glass_time_studio.global.security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenizer {

    // application.yml 에서 변수를 불러오도록 하는 애너테이션 Value.
    @Getter
    @Value("${jwt.key.secret}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey){
        // 문자열 SecretKey를 JWT 라이브러리에서 제공하는 Base64 형식으로 인코딩하여 반환.
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // AccessToken을 생성하는 메서드
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey){
        // Key 객체의 key는 Base64형식으로 인코딩된 문자열 SecretKey를 디코딩한 순수 문자열 SecretKey
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        // 전달받은 claims에 email을 추가 (이미 되었다면 생략)
        claims.putIfAbsent("email", subject);

        //전달받은 claims, subject, IssuedAt, Expiration, key를 사용해 compact string 타입으로 발급
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
    // RefreshToken 생성 메서드
    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String base64EncodedSecretKey){
        // Key 객체의 key는 Base64형식으로 인코딩된 문자열 SecretKey를 디코딩한 순수 문자열 SecretKey
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        String refreshToken = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        return refreshToken;
    }
    // 처음 발급받은 RefreshToken을 가지고 2번째 AccessToken을 생성하는 메서드
    public String regenerateAccessToken(String refreshToken){
        try{
            // RefreshToken과 SecretKey를 Base64로 인코딩한 키를 입력받고,
            // 그 RefreshToken을 디코딩하여 Jws<Claims>객체 refreshTokenClaims에 담는다.
            Jws<Claims> refreshTokenClaims = getClaims(refreshToken, encodeBase64SecretKey(secretKey));

            // refreshTokenClaims 객체로부터 exp(토근 만료 타임스탬프)를 int 타입으로 가져옴.
            int epochTime = (Integer) refreshTokenClaims.getBody().get("exp");

            // RefreshToken 으로부터 만료시간을 계산하여 Date 객체 할당
            // milliseconds 로 변경하기 위해 1000을 곱함.
            Date refreshTokenExpiration = new Date(epochTime * 1000L);

            // refreshToken 만료시간을 계산하여 현재 시간과 비교
            if(refreshTokenExpiration.before(new Date())){
                //만료되었다면 RefreshToken has expired 메시지와 예외 처리.
                throw new RuntimeException("RefreshToken has expired");
            }
            // refreshToken 이 유효하다면 refreshTokenClaims 객체로부터 getSubject 메서드로 이메일 확보
            String email = refreshTokenClaims.getBody().getSubject();

            // 새로운 AccessToken의 만료 기간을 필드 accessTokenExpirationMinutes 변수를 통해 할당
            Date accessTokenExpiration = getTokenExpiration(accessTokenExpirationMinutes);

            // 새로운 accessToken의 claims를 위해 Map 객체 생성해 email을 할당.
            Map<String, Object> accessTokenClaims = new HashMap<>();
            accessTokenClaims.put("email", email);

            //새로운 AccessToken을 생성하여, claims, email, 만료날짜와 Base64로 인코딩된 SecretKey를 Param 사용
            String newAccessToken = generateAccessToken(accessTokenClaims, email,
                    accessTokenExpiration, encodeBase64SecretKey(secretKey));
            return newAccessToken;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Jwt 토큰과 Base64 형식으로 인코딩된 SecretKey를 입력받음.
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey){
        // Base64로 인코딩된 SecretKey를 getKeyFromBase64EncodedKey 메서드로 디코딩해 key 생성.
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        // Token을 디코딩하여 token claims 반환
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }
    // Jwt Token과 Base64 형식의 인코딩 SecretKey를 입력받고, Token의 Signature를 검증
    public void verifySignature(String jws, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }
    // 구체적인 Minutes 단위의 값을 전달받아 Token의 만료날짜를 계산
    public Date getTokenExpiration(int expirationMinutes){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();
        return expiration;
    }
    // JWT 라이브러리를 사용하여 Base64 형식의 인코딩된 SecretKey를 디코딩하여 Key 객체 반환
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey){
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
}
