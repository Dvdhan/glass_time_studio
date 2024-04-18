package David.glass_time_studio.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ComponentConfiguration {
    @Bean
    public TokenRedisRepository tokenRedisRepository(StringRedisTemplate stringRedisTemplate){
        return new TokenRedisRepository(stringRedisTemplate);
    }
}
