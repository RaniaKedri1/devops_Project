package org.example.sheltercare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan("org.example.sheltercare")
public class ShelterCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterCareApplication.class, args);
    }

}
