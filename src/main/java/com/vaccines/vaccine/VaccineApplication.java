package com.vaccines.vaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Date;
@SpringBootApplication
public class VaccineApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccineApplication.class, args);
		System.out.print(new Date());
	}

}
