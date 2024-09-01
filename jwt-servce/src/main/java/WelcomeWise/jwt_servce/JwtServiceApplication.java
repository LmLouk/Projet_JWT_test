package WelcomeWise.jwt_servce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "WelcomeWise.jwt_servce.Authentification")
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "WelcomeWise.jwt_servce.Authentification.Repository")
@EntityScan(basePackages = "WelcomeWise.jwt_servce.Authentification.Model")
public class JwtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtServiceApplication.class, args);
	}

}
