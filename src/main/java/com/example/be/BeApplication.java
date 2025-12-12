package com.example.be;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("OPENAI_API_KEY", getEnv("OPENAI_API_KEY", dotenv));

		System.setProperty("POSTGRES_URL", getEnv("POSTGRES_URL", dotenv));
		System.setProperty("POSTGRES_USER", getEnv("POSTGRES_USER", dotenv));
		System.setProperty("POSTGRES_PASSWORD", getEnv("POSTGRES_PASSWORD", dotenv));

		SpringApplication.run(BeApplication.class, args);
	}

	private static String getEnv(String key, Dotenv dotenv) {
		String value = System.getenv(key);
		return (value != null) ? value : dotenv.get(key);
	}

}
