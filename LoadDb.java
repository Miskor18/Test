package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDb {

	@Bean
	CommandLineRunner initDatabase(ClientStorage clientStorage) {
		return args -> {
			clientStorage.save(new Client("Captain Danko", "cpt@sns.com", "05044574256"));
			clientStorage.save(new Client("Hulk", "hulk@avenge.org", "??????????"));
			clientStorage.save(new Client("Turzonova", "tur@ova.net", "15485426874"));
		};
	}
}
