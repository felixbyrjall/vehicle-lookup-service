package com.github.felixbyrjall.vehiclelookup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleLookupApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleLookupApplication.class, args);
		System.out.println("H2 Console URL: http://localhost:8080/h2-console-vehicle-lookup/");
		System.out.println("JDBC URL: jdbc:h2:file:./data/vehicle-cache-db");
	}
}
