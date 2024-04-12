package David.glass_time_studio.domain.login_logout;

import David.glass_time_studio.global.security.JwtTokenizer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/glass_time_studio")
@Slf4j
public class Login_LogoutController {

    private JwtTokenizer jwtTokenizer;
    private TokenRedisRepository tokenRedisRepository;

    public Login_LogoutController(JwtTokenizer jwtTokenizer, TokenRedisRepository tokenRedisRepository){
        this.jwtTokenizer=jwtTokenizer;
        this.tokenRedisRepository=tokenRedisRepository;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String accessTokenHeader = request.getHeader("Authorization");
        String refreshTokenHeader = request.getHeader("Refresh");
        if (accessTokenHeader != null && accessTokenHeader.startsWith("Bearer ")){
            String accessToken = accessTokenHeader.replace("Bearer ", "");
            int accessTokenDuration = jwtTokenizer.getAccessTokenExpirationMinutes();
            tokenRedisRepository.saveInvalidatedAccessToken(accessToken, accessTokenDuration, TimeUnit.MINUTES);
        }
        if(refreshTokenHeader != null){
            String refreshToken = refreshTokenHeader;
            int refreshTokenDuration = jwtTokenizer.getRefreshTokenExpirationMinutes();
            tokenRedisRepository.saveInvalidatedRefreshToken(refreshToken, refreshTokenDuration, TimeUnit.MINUTES);
        }
        return ResponseEntity.ok("Logout Successful");
    }
}
