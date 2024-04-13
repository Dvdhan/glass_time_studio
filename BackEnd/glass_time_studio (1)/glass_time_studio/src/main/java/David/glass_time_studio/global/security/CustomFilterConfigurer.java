package David.glass_time_studio.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    private final JwtVerificationFilter jwtVerificationFilter;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
    }
}
