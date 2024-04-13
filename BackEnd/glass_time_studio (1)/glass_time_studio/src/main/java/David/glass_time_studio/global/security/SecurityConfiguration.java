package David.glass_time_studio.global.security;

import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableGlobalMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.client.registration.naver.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.clientSecret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirectUri}")
    private String redirectUri;

    private final JwtTokenizer jwtTokenizer;
    private final glassTimeStudioAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CustomFilterConfigurer customFilterConfigurer;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new glassTimeStudioAuthenticationEntryPoint())
                .accessDeniedHandler(new glassTimeStudioDeniedHandler())
                .and()
                .anonymous().disable()
                .apply(customFilterConfigurer)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/member/logout").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/member").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/member").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/member/mypage").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/member/{member_Id}").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                        ).oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2SuccessHandler(jwtTokenizer, authorityUtils, memberService, memberRepository,
                                oAuth2AuthorizedClientService))).cors(withDefaults());
        return http.build();
    }
    // CORS 설정 메서드
    @Bean
    CorsConfiguration corsConfiguration() {
        // CORS 설정에 대한 객체
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PATCH","DELETE","OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList("*")); // 서버 측에서 제공하는 추가적인 헤더 명시
        configuration.setAllowedHeaders(Arrays.asList("*")); // 요청 측에서 제공하는 추가적인 헤더 명서

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
//        return source;
        return null;
    }
}
