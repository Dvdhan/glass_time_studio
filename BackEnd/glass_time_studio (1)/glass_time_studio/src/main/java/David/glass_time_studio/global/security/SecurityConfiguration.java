package David.glass_time_studio.global.security;

import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.global.security.JWT.CustomFilterConfigurer;
import David.glass_time_studio.global.security.JWT.JwtTokenizer;
import David.glass_time_studio.global.security.JWT.glassTimeStudioAuthorityUtils;
import David.glass_time_studio.global.security.OAuth2.OAuth2SuccessHandler;
import David.glass_time_studio.global.security.OAuth2.glassTimeStudioAuthenticationEntryPoint;
import David.glass_time_studio.global.security.OAuth2.glassTimeStudioDeniedHandler;
import David.glass_time_studio.global.security.OAuth2.glassTimeStudioOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;

    private final JwtTokenizer jwtTokenizer;
    private final glassTimeStudioAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CustomFilterConfigurer customFilterConfigurer;
    private final glassTimeStudioOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new glassTimeStudioAuthenticationEntryPoint())
                        .accessDeniedHandler(new glassTimeStudioDeniedHandler())
                )
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
////                        .requestMatchers(HttpMethod.GET,"/member/login").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/main").permitAll()
//                        .requestMatchers("/WEB-INF/views/**/*.jsp").permitAll()
//                        .anyRequest().authenticated()
//                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2SuccessHandler(jwtTokenizer, authorityUtils, memberService, memberRepository))
                );
        return http.build();
    }
    // CORS 설정 메서드
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // CORS 설정에 대한 객체
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PATCH","DELETE","OPTIONS"));

        configuration.setExposedHeaders(Arrays.asList("*")); // 서버 측에서 제공하는 추가적인 헤더 명시
        configuration.setAllowedHeaders(Arrays.asList("*")); // 요청 측에서 제공하는 추가적인 헤더 명서

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
