package David.glass_time_studio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GlassTimeStudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlassTimeStudioApplication.class, args);
	}
}
