package org.launchcode.adventureland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
public class AdventurelandApplication {



	public static void main(String[] args) {
		SpringApplication.run(AdventurelandApplication.class, args);
	}

}
