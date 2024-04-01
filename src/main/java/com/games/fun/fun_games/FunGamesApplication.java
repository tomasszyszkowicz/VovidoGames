package com.games.fun.fun_games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The FunGamesApplication class is the entry point for the Fun Games application.
 * It initializes and starts the Spring Boot application.
 */
@SpringBootApplication
public class FunGamesApplication {

	/**
	 * The main method is the entry point for the application.
	 * It starts the Spring Boot application by calling SpringApplication.run() method.
	 *
	 * @param args The command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FunGamesApplication.class, args);
	}

}
