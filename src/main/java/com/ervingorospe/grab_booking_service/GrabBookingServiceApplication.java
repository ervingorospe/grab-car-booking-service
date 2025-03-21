package com.ervingorospe.grab_booking_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrabBookingServiceApplication {

	public static void main(String[] args) {
        // Load .env file

        String isKubernetes = System.getenv("KUBERNETES_SERVICE_HOST");

        if (isKubernetes == null) {
            // Running locally (IntelliJ)
            Dotenv dotenv = Dotenv.configure().load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
            System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
            System.setProperty("MONGO_BOOKING_USERNAME", dotenv.get("MONGO_BOOKING_USERNAME"));
            System.setProperty("MONGO_BOOKING_PASSWORD", dotenv.get("MONGO_BOOKING_PASSWORD"));
            System.setProperty("MONGO_BOOKING_URL", dotenv.get("MONGO_BOOKING_URL"));
        } else {
            // Running in Kubernetes (use environment variables)
            System.setProperty("DB_URL", System.getenv("DB_URL"));
            System.setProperty("DB_USERNAME", System.getenv("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));
            System.setProperty("DB_DRIVER", System.getenv("DB_DRIVER"));
            System.setProperty("JWT_SECRET", System.getenv("JWT_SECRET"));
            System.setProperty("MONGO_BOOKING_USERNAME", System.getenv("MONGO_BOOKING_USERNAME"));
            System.setProperty("MONGO_BOOKING_PASSWORD", System.getenv("MONGO_BOOKING_PASSWORD"));
            System.setProperty("MONGO_BOOKING_URL", System.getenv("MONGO_BOOKING_URL"));
        }

        SpringApplication.run(GrabBookingServiceApplication.class, args);
	}

}
