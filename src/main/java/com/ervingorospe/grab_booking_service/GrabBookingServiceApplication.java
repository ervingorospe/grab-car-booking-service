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
            System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
            System.setProperty("MONGO_BOOKING_USERNAME", dotenv.get("MONGO_BOOKING_USERNAME"));
            System.setProperty("MONGO_BOOKING_PASSWORD", dotenv.get("MONGO_BOOKING_PASSWORD"));
            System.setProperty("MONGO_BOOKING_URL", dotenv.get("MONGO_BOOKING_URL"));
            System.setProperty("MONGO_PREFIX", dotenv.get("MONGO_PREFIX"));

        } else {
            // Running in Kubernetes (use environment variables)
            System.setProperty("JWT_SECRET", System.getenv("JWT_SECRET"));
            System.setProperty("MONGO_BOOKING_USERNAME", System.getenv("MONGO_BOOKING_USERNAME"));
            System.setProperty("MONGO_BOOKING_PASSWORD", System.getenv("MONGO_BOOKING_PASSWORD"));
            System.setProperty("MONGO_BOOKING_URL", System.getenv("MONGO_BOOKING_URL"));
            System.setProperty("MONGO_PREFIX", System.getenv("MONGO_PREFIX"));
        }

        SpringApplication.run(GrabBookingServiceApplication.class, args);
	}

}
