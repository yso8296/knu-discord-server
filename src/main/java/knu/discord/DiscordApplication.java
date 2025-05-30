package knu.discord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DiscordApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordApplication.class, args);
	}

}
