package kseoni.ch.pkmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kseoni.ch.pkmn")
public class PkmnApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkmnApplication.class, args);
	}

}
